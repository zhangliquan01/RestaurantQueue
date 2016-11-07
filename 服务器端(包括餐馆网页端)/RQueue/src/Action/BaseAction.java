package Action;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;

public class BaseAction {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	private JSONObject jObject;
	private JSONObject returnJObject;
	public BaseAction(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
		try {
			this.request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		returnJObject = new JSONObject();
	}
	public JSONObject getData() {
		Object object = null;
		
		try {
			inputStream = request.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			object = objectInputStream.readObject();
			inputStream.close();
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		jObject = JSONObject.fromObject(object);
		
		return jObject;
	}
	public void addReturnData(String key,Object value) {
		returnJObject.put(key, value);
	}
	public void addReturnData(String key,byte[] bts) {
		String data = Base64.encodeBase64String(bts);
		addReturnData(key, data);
	}
	public void returnData(){
		try {
			outputStream = response.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(returnJObject.toString());
			objectOutputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getValueString(String key){
		return jObject.getString(key);
	}

	public byte[] getImage(String key){
		String imageStr = jObject.getString(key);
		byte[] btsBytes = Base64.decodeBase64(imageStr);
		return btsBytes;
//		FileOutputStream outputStream = new FileOutputStream(new File("D:\\2.jpg"));
//		outputStream.write(btsBytes);
//		outputStream.close();
	}
}
