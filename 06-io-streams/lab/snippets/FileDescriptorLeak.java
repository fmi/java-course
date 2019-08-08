package bg.sofia.uni.fmi.mjt.streams;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileDescriptorLeak {

	private static int index_count;

	public static void main(String[] args) throws IOException {
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				index_count++;

				File file = new File("/tmp/helloworld.txt");
				FileDescriptor fileDescriptor;
				FileOutputStream outputStream;
				try {
					outputStream = new FileOutputStream(file);
					fileDescriptor = outputStream.getFD();

					// passing FileDescriptor to another FileOutputStream
					FileOutputStream outputStreamTwo = new FileOutputStream(fileDescriptor);
					outputStreamTwo.write(index_count++);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 0, 5, TimeUnit.SECONDS);
	}
}
