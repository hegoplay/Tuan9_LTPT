package bai7;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.ToString
public class FileInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3741709546404452992L;
	
	private String destDict;
	private String fileName;
	private long fileSize;
	private int piecesOfFile;
	private int lastByteLength;
	private String status;
	
}
