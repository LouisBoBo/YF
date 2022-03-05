package com.yssj.ui.activity.testfile;

import android.content.Context;
import com.yssj.YUrl;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpLoadUtil {


    /**
     * 上传图片
     *
     * @param imagePath 图片路径
     * @return 新图片的路径
     * @throws IOException
     * @throws JSONException
     */
    @NotNull
    public static void uploadImage(Context context, String imagePath, final UpLoadImgListener upLoadImgListener) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(imagePath);
        String imgName = file.getName();
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        Request.Builder builder = new Request.Builder();
        builder.addHeader("token", PreferencesUtil.getString(context, "user_token", ""));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imgName, image)
//                .addFormDataPart("file", imagePath, image)
                .addFormDataPart("fileType", "1")
                .build();
        Request request = builder
                .url(YUrl.imgurl + "要上传的图片地址")
                .post(requestBody)
                .build();


        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                upLoadImgListener.upLoadFail();
                android.util.Log.e("上传图片", "失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    UpLoadImageResult result = JsonUtils.parseObject(jsonObject.toString(), UpLoadImageResult.class);


                    if (result.getCode() != 200) {
                        upLoadImgListener.upLoadFail();
                        android.util.Log.e("上传图片", "失败" + result.getMessage());

                    } else {
                        upLoadImgListener.upLoadSuccess(result.getData());
                        android.util.Log.e("上传图片", "成功" + jsonObject.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface UpLoadImgListener {
        void upLoadSuccess(int imgId);

        void upLoadFail();
    }


}
