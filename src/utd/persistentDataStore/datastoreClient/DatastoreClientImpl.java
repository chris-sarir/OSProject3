package utd.persistentDataStore.datastoreClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;

public class DatastoreClientImpl implements DatastoreClient
{
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;

	public DatastoreClientImpl(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String, byte[])
	 */
	@Override
    public void write(String name, byte data[]) throws ClientException
	{
		try {
			logger.debug("Executing Write Operation");

			String aRequest="write\n"+name+"\n" + data.toString() + "\n" + data.length;
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			logger.debug("Sending request");
			StreamUtil.writeData(aRequest.getBytes(),outputStream);
			logger.debug("Sending Successful");
			
			String aResponse = StreamUtil.readLine(inputStream);
			logger.debug("Response was " + aResponse);
			
			StreamUtil.closeSocket(inputStream);
			logger.debug("Socket Closed");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
	 */
	@Override
    public byte[] read(String name) throws ClientException
	{
		logger.debug("Executing Read Operation");
		byte[] result = null;
		
		try {
			
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Writing Message");
			StreamUtil.writeLine("read\n", outputStream);
			StreamUtil.writeLine(name, outputStream);
			
			logger.debug("Reading Response");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch;
			int i = 0;
			
			//While loop keeps track of the number of new lines in the stream
			//Once three are found break the loop
			//ok\n
			//data size in ascii bytes\n
			//N bytes of binary data\n
			
			while( i < 3 ){
				ch = inputStream.read();
				baos.write(ch);
				if(ch == '\n'){
					i++;
				}
			}
			
			result = baos.toByteArray();
			StreamUtil.closeSocket(inputStream);
			
			logger.debug("Response " + result);
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
	 */
	@Override
    public void delete(String name) throws ClientException
	{
		logger.debug("Executing Delete Operation");
		byte[] result;

		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			logger.debug("Writing Message");
			StreamUtil.writeLine("delete\n", outputStream);
			StreamUtil.writeLine(name, outputStream);

			logger.debug("Reading Response");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch;
			int i = 0;

			//While loop keeps track of the number of new lines in the stream
			//Once one is found break the loop
			//ok\n

			while( i < 1 ){
				ch = inputStream.read();
				baos.write(ch);
				if(ch == '\n'){
					i++;
				}
			}

			result = baos.toByteArray();
			StreamUtil.closeSocket(inputStream);

			logger.debug("Response " + result);
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 */
	@Override
    public List<String> directory() throws ClientException
	{
		//logger.debug("Executing Directory Operation");
		List<String> dir = new ArrayList<String>();

		try {

			//logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			//logger.debug("Writing Message: directory");
			StreamUtil.writeLine("directory\n", outputStream);

			//logger.debug("Reading Message: directory");

			String directoryData = StreamUtil.readLine(socket.getInputStream());
			dir.add(directoryData);
			directoryData = StreamUtil.readLine(socket.getInputStream());
			dir.add(directoryData);
			int count = Integer.parseInt(directoryData);
			for(;count >0 ;count++){
				directoryData = StreamUtil.readLine(socket.getInputStream());
				dir.add(directoryData);
			}
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}

		return dir;
	}

}
