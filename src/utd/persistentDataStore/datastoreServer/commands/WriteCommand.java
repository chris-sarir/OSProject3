package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand {

	@Override
	public void run() throws IOException, ServerException {

		File dir = new File("data");
		
		if(!dir.exists()){
			dir.mkdir();
		}
		
		String name = StreamUtil.readLine(inputStream);
		logger.debug("Received file name: " + name);
		String data = StreamUtil.readLine(inputStream);
		
		
		int d = Integer.parseInt(data);
		logger.debug("Received data size: " + d);
		
		byte[] dataBin = StreamUtil.readData(d, inputStream);
		logger.debug("Received data: " + dataBin.length);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((data + "\n").getBytes());
		baos.write(dataBin);
		
		FileUtil.writeData(name, baos.toByteArray());
		logger.debug("File created");
		sendOK();
	}

}
