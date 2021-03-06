package com.mao.duoduo.activity.impl;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.mao.cropimage.UCrop;
import com.mao.duoduo.R;
import com.mao.duoduo.bean.User;
import com.mao.duoduo.widget.CircleImageView;
import com.mao.duoduo.widget.SelectPicturePopupWindow;
import com.mao.utils.MaoLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Mao on 16-12-20.
 */
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener,
        SelectPicturePopupWindow.OnSelectedListener {

    private static final String TAG = "PersonalActivity";

    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记

    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    // 拍照临时图片
    private String mTempPhotoPath;
    // 剪切后图像文件
    private Uri mDestinationUri;

    private TextView mTvSelect;
    private ImageView mIvPicture;
    private CircleImageView mCiHeader;

    private AlertDialog mAlertDialog;
    private SelectPicturePopupWindow mPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
        mDestinationUri = Uri.fromFile(new File(getCacheDir(), "cropImage.jpeg"));
    }

    private void initView() {
        setContentView(R.layout.activity_personal);
        mTvSelect = (TextView) findViewById(R.id.tv_selected);
        mTvSelect.setOnClickListener(this);
        mIvPicture = (ImageView) findViewById(R.id.iv_pic);
        mCiHeader = (CircleImageView) findViewById(R.id.iv_circle_pic);
        mPopWindow = new SelectPicturePopupWindow(this);
        mPopWindow.setOnSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_selected:
                mPopWindow.showPopupWindow(PersonalActivity.this);
                break;
        }
    }

    @Override
    public void OnSelected(View v, int position) {
        switch (position) {
            case 0:
                // "拍照"按钮被点击了
                takePhoto();
                break;
            case 1:
                // "从相册选择"按钮被点击了
                pickFromGallery();
                break;
            case 2:
                // "取消"按钮被点击了
                mPopWindow.dismissPopupWindow();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:   // 调用相机拍照
                    File temp = new File(mTempPhotoPath);
                    startCropActivity(Uri.fromFile(temp));
                    break;
                case GALLERY_REQUEST_CODE:  // 直接从相册获取
                    startCropActivity(data.getData());
                    break;
                case UCrop.REQUEST_CROP:    // 裁剪图片结果
                    handleCropResult(data);
                    break;
                case UCrop.RESULT_ERROR:    // 裁剪图片错误
                    handleCropError(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .withTargetActivity(CropActivity.class)
                .start(this);
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCiHeader.setImageBitmap(bitmap);
            mIvPicture.setImageBitmap(bitmap);

            upLoadHeader(resultUri);
        } else {
            Toast.makeText(this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 上传头像文件
     * @param headerUri
     */
    private void upLoadHeader(Uri headerUri) {
        final BmobFile headFile = new BmobFile(new File(Uri.decode(headerUri.getEncodedPath())));
        headFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (null == e) {
                    //headFile.getFileUrl()--返回的上传文件的完整地址
                    MaoLog.i(TAG, "Header file upload success ， 文件地址 :　" + headFile.getFileUrl());
//                    deleteLastAvatar();
                    updateUser(headFile);
                } else {
                    MaoLog.i(TAG, "Header file upload failure : " + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                MaoLog.i(TAG, "Progress : " + value);
            }
        });
    }

    /**
     * 删除原来的头像
     */
    private void deleteLastAvatar() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobFile bmobFile = new BmobFile();
        bmobFile.setUrl(user.getAvatar());
        bmobFile.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    MaoLog.i(TAG, "Delete header pic success");
                } else {
                    MaoLog.i(TAG, "Delete header pic failure : " + e.getMessage());
                }
            }
        });
    }

    /**
     * 更新用户 头像 数据
     */
    private void updateUser(BmobFile headFile) {
        User user = BmobUser.getCurrentUser(User.class);
        user.setAvatar(headFile.getFileUrl());
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    MaoLog.i(TAG, "Update success");
                } else {
                    MaoLog.i(TAG, "Update failure : " + e.getMessage());
                }
            }
        });
    }

    /**
     * 删除拍照临时文件
     */
    private void deleteTempPhotoFile() {
        File tempFile = new File(mTempPhotoPath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }

    /**
     * 处理剪切失败的返回值
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            mPopWindow.dismissPopupWindow();
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //下面这句指定调用相机拍照后的照片存储的路径
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mTempPhotoPath)));
            startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 请求权限
     * <p>
     * 如果权限被拒绝过，则提示用户需要权限
     */
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        // android 6.0要手动申请权限， 9大dangerous permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(permission)) {
                showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{permission}, requestCode);
                            }
                        }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
            } else {
                requestPermissions(new String[]{permission}, requestCode);
            }
        }
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    private void showAlertDialog(@Nullable String title, @Nullable String message,
                                 @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                 @NonNull String positiveText,
                                 @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                 @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            mPopWindow.dismissPopupWindow();
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

}
