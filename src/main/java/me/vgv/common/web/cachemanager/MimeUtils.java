package me.vgv.common.web.cachemanager;

import javax.activation.MimetypesFileTypeMap;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class MimeUtils {

	private static final ThreadLocal<MimetypesFileTypeMap> MIMETYPES_FILE_TYPE_MAP_THREAD_LOCAL = new ThreadLocal<MimetypesFileTypeMap>() {
		@Override
		protected MimetypesFileTypeMap initialValue() {
			MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
			mimetypesFileTypeMap.addMimeTypes("text/css                        css");
			mimetypesFileTypeMap.addMimeTypes("text/html                       html");
			mimetypesFileTypeMap.addMimeTypes("application/javascript          js");
			mimetypesFileTypeMap.addMimeTypes("image/gif                       gif");
			mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-fontobject   eot");
			mimetypesFileTypeMap.addMimeTypes("application/font-woff           woff");
			return mimetypesFileTypeMap;
		}
	};

	public static String getMimeType(String fileName) {
		return MIMETYPES_FILE_TYPE_MAP_THREAD_LOCAL.get().getContentType(fileName);
	}

}
