package com.dhxgzs.goodluck.sharefragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import com.bumptech.glide.Glide;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.ImageUtil;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FenXiangLianJieFragment extends Fragment implements OnClickListener {

	private View view;
	private ImageView fenxiang_fuzhi;
	private ImageView fenxiang_xiazai;
	private ImageView fenxiang_Img;
	private TextView fuzhiContent;
	private TextView share_ID;
	private String ShareID;
	private static final String OBJECT_IMAGE_URL 
	= "http://qr.liantu.com/api.php?w=200&text="+XyMyContent.HOST_URL+"/wap/register?pid=";

	private CustomProgressBarDialog dialog;
	private static final int HANDLER_LOADING = 262;

    /**
     * ˢ��Dialog��ʾ�Ľ���Handler
     */
    private static class LoadingHandler extends Handler {
        private final WeakReference<FenXiangLianJieFragment> mActivity;

        public LoadingHandler(FenXiangLianJieFragment activity) {
            mActivity = new WeakReference<FenXiangLianJieFragment>(activity);
        }

        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            FenXiangLianJieFragment activity = this.mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case HANDLER_LOADING: {
                        int progressValue =   (Integer) msg.obj;
                        activity.dialog.setLoadPrompt(progressValue + "%");
                        activity.dialog.show();
                        break;
                    }
                }
            }
        }
    }
    private final LoadingHandler loadingHandler = new LoadingHandler(FenXiangLianJieFragment.this);
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_fenxianglianjie, null);

		initView();
		setListener();

		return view;
	}

	private void initView() {
		ShareID=SharedPreferencesUtils.getValue(getActivity(), "USERID");
		fenxiang_xiazai = (ImageView) view.findViewById(R.id.fenxiang_xiazai);
		fenxiang_fuzhi = (ImageView) view.findViewById(R.id.fenxiang_fuzhi);
		fenxiang_Img = (ImageView) view.findViewById(R.id.fenxiang_Img);
		fuzhiContent = (TextView) view.findViewById(R.id.fuzhiContent);
		share_ID = (TextView) view.findViewById(R.id.share_ID);
		share_ID.setText(ShareID);
		this.dialog = new CustomProgressBarDialog(getContext());
		fuzhiContent.setText(XyMyContent.HOST_URL+"/wap/register?pid="+share_ID);
		// ��������ͷ��
		Glide.with(getActivity()).load(OBJECT_IMAGE_URL+ShareID).asBitmap()
				.error(R.drawable.touxiang).into(fenxiang_Img);
	}

	private void setListener() {

		fenxiang_xiazai.setOnClickListener(this);
		fenxiang_fuzhi.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.fenxiang_xiazai:// ����
			v.setEnabled(false);
            /**
             * ���ð�ť�����ã���ʼִ������
             */
            new DownloadImageAsyncTask(getActivity()).execute(OBJECT_IMAGE_URL+ShareID);
			break;
		case R.id.fenxiang_fuzhi:// ����
			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this.getActivity()
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(fuzhiContent.getText().toString());
			} else {
				// �õ������������
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setPrimaryClip(ClipData.newPlainText(null, fuzhiContent.getText().toString()));
			}
			Toast.makeText(getActivity(), "���Ƶ����а�", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	/**
     * �Զ��������Dialog
     */
    public class CustomProgressBarDialog extends Dialog {

        private LayoutInflater mInflater;
        private Context mContext;
        private WindowManager.LayoutParams params;
        private View mView;
        private TextView promptTV;

        public CustomProgressBarDialog(Context context) {
            super(context);
            this.init(context);
        }

        public CustomProgressBarDialog(Context context, int themeResId) {
            super(context, themeResId);
            this.init(context);
        }

        protected CustomProgressBarDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
            this.init(context);
        }
        private void init(Context context) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.mContext = context;
            this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mView = this.mInflater.inflate(R.layout.dialog_progressbar, null);
            setContentView(this.mView);

            // ����window����
            this.params = getWindow().getAttributes();
            this.params.gravity = Gravity.CENTER;
            // ȥ�����ڸ�
            this.params.dimAmount = 0;
            this.params.alpha = 1.0f;
            // ���ܹص�
            this.setCancelable(false);
            this.getWindow().setAttributes(this.params);
            this.promptTV = (TextView) findViewById(R.id.load_info_text);
        }
        /**
         * ��������
         *
         * @param prompt
         */
        public void setLoadPrompt(String prompt) {
            this.promptTV.setText(prompt);
        }
    }
    /**
     * ����ͼƬ�첽����
     */
    public class DownloadImageAsyncTask extends AsyncTask<String, Integer, String> {

        private Activity activity;
        private String localFilePath;

        public DownloadImageAsyncTask(Activity activity) {
            super();
            this.activity = activity;
        }

        /**
         * ��ӦAsyncTask��һ������
         * �첽������������UI�߳��У����ܶԿؼ������޸�
         * ���Ե���publishProgress������ת��onProgressUpdate(���������һ��handler.sendMessage(...)�Ĺ���)
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {
            // TODO ע������
            /**
             * ������������õ�������ȥ����ͼƬ��ֻҪ��֤this.localFilePath��ֵ�оͿ�����
             */
            URL fileUrl = null;
            try {
                fileUrl = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (fileUrl == null) return null;
            try {
                HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                //�����ļ�����
                int lengthOfFile = connection.getContentLength();
                /**
                 * ������SD�����ͷŵ������ļ�����
                 */
                File cacheDir = this.activity.getCacheDir();
                File downloadFile = new File(cacheDir, UUID.randomUUID().toString() + ".jpg");
                this.localFilePath = downloadFile.getPath();
                if (!downloadFile.exists()) {
                    File parent = downloadFile.getParentFile();
                    if (parent != null) parent.mkdirs();
                }
                FileOutputStream output = new FileOutputStream(downloadFile);
                InputStream input = connection.getInputStream();
                InputStream bitmapInput = connection.getInputStream();
                //����
                byte[] buffer = new byte[1024];
                int len;
                long total = 0;
                // �������
                while ((len = input.read(buffer)) > 0) {
                    total += len;
                    this.publishProgress((int) ((total * 100) / lengthOfFile));
                    output.write(buffer, 0, len);
                }
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * ��ӦAsyncTask���������� (����doInBackground�ķ���ֵ)
         * ��doInBackground����ִ�н���֮�������У���ʱ�Ѿ�������UI�̵߳��� �ܶ�UI�ؼ������޸�
         *
         * @param string The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String string) {
            /**
             * ���ð�ť���ã�������Dialog
             */
            FenXiangLianJieFragment.this.fenxiang_xiazai.setEnabled(true);
            FenXiangLianJieFragment.this.dialog.hide();

            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;
            /**
             * ImageUtil.decodeScaleImage ����ͼƬ
             */
            Bitmap bitmap = ImageUtil.decodeScaleImage(this.localFilePath, screenWidth, screenHeight);
//            FenXiangLianJieFragment.this.fenxiang_xiazai.setImageBitmap(bitmap);
            /**
             * ����ͼƬ�����
             */
            String imageName = System.currentTimeMillis() + ".jpg";
            MediaStore.Images.Media.insertImage(FenXiangLianJieFragment.this.getActivity().getContentResolver(), bitmap, imageName, "��ҽ����");
            Toast.makeText(this.activity, "�ѱ��棺" + imageName, Toast.LENGTH_LONG).show();
        }

        /**
         * ��ӦAsyncTask�ڶ�������
         * ��doInBackground�������У�ÿ�ε���publishProgress����������ת(handler.sendMessage(...))��onProgressUpdate
         * ����UI�߳��У����ԶԿؼ������޸�
         *
         * @param values The values indicating progress.
         * @see #publishProgress
         * @see #doInBackground
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            // ���߳�Handlerʵ����Ϣ
            Message message = FenXiangLianJieFragment.this.loadingHandler.obtainMessage();
            message.obj = values[0];
            message.what = HANDLER_LOADING;
            // �����߳�Handler������Ϣ
            FenXiangLianJieFragment.this.loadingHandler.handleMessage(message);
        }

        /**
         * ��������UI�߳��У���ʱ��Ԥִ��״̬����һ����doInBackground
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * <p>Applications should preferably override {@link #onCancelled(Object)}.
         * This method is invoked by the default implementation of
         * {@link #onCancelled(Object)}.</p>
         * <p/>
         * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
         * {@link #doInBackground(Object[])} has finished.</p>
         *
         * @see #onCancelled(Object)
         * @see #cancel(boolean)
         * @see #isCancelled()
         */
        @Override
        protected void onCancelled() {
            /**
             * ���ð�ť����
             */
            FenXiangLianJieFragment.this.fenxiang_xiazai.setEnabled(true);
            super.onCancelled();
        }

    }
}
