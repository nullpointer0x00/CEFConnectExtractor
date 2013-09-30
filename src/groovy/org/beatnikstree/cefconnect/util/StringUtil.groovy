package org.beatnikstree.cefconnect.util

class StringUtil {

	public static getStringFromInputStream(InputStream is){
		def br
		def sb = new StringBuffer(1024);
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is))
			while ((line = br.readLine()) != null) {
				sb.append(line)
			}
 
		} catch (IOException e) {
			e.printStackTrace()
		} finally {
			if (br != null) {
				try {
					br.close()
				} catch (IOException e) {
					e.printStackTrace()
				}
			}
		}
		return sb.toString().trim()
	}
	
}
