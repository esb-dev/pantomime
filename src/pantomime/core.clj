(ns pantomime.core
  (:import (java.io File)))

;; Borrowed from the MIT-licensed Ring library by Mark McGranaghan (https://github.com/mmcgrana/ring)
;; with additions from the BSD-licensed default Nginx () mime types list.
(def ^:dynamic *mime-types*
  {"7z"    "application/x-7z-compressed"
   "aac"   "audio/aac"
   "ai"    "application/postscript"
   "asc"   "text/plain"
   "atom"  "application/atom+xml"
   "avi"   "video/x-msvideo"
   "bin"   "application/octet-stream"
   "bmp"   "image/bmp"
   "bz2"   "application/x-bzip"
   "class" "application/octet-stream"
   "cer"   "application/pkix-cert"
   "crl"   "application/pkix-crl"
   "crt"   "application/x-x509-ca-cert"
   "css"   "text/css"
   "csv"   "text/csv"
   "deb"   "application/x-deb"
   "dll"   "application/octet-stream"
   "dmg"   "application/octet-stream"
   "dms"   "application/octet-stream"
   "doc"   "application/msword"
   "dvi"   "application/x-dvi"
   "ear"   "application/java-archive"
   "eot"   "application/octet-stream"
   "eps"   "application/postscript"
   "etx"   "text/x-setext"
   "exe"   "application/octet-stream"
   "flv"   "video/x-flv"
   "flac"  "audio/flac"
   "gif"   "image/gif"
   "gz"    "application/gzip"
   "htm"   "text/html"
   "html"  "text/html"
   "ico"   "image/x-icon"
   "img"   "application/octet-stream"
   "iso"   "application/x-iso9660-image"
   "jar"   "application/java-archive"
   "jng"   "image/x-jng"
   "jpe"   "image/jpeg"
   "jpeg"  "image/jpeg"
   "jpg"   "image/jpeg"
   "js"    "text/javascript"
   "json"  "application/json"
   "lha"   "application/octet-stream"
   "lzh"   "application/octet-stream"
   "mov"   "video/quicktime"
   "m4v"   "video/mp4"
   "mp3"   "audio/mpeg"
   "mp4"   "video/mp4"
   "mpe"   "video/mpeg"
   "mpeg"  "video/mpeg"
   "mpg"   "video/mpeg"
   "msi"   "application/octet-stream"
   "msm"   "application/octet-stream"
   "msp"   "application/octet-stream"
   "oga"   "audio/ogg"
   "ogg"   "audio/ogg"
   "ogv"   "video/ogg"
   "pbm"   "image/x-portable-bitmap"
   "pdf"   "application/pdf"
   "pgm"   "image/x-portable-graymap"
   "png"   "image/png"
   "pnm"   "image/x-portable-anymap"
   "ppm"   "image/x-portable-pixmap"
   "ppt"   "application/vnd.ms-powerpoint"
   "ps"    "application/postscript"
   "qt"    "video/quicktime"
   "ra"    "audio/x-realaudio"
   "rar"   "application/x-rar-compressed"
   "ras"   "image/x-cmu-raster"
   "rb"    "text/plain"
   "rd"    "text/plain"
   "rss"   "application/rss+xml"
   "rtf"   "application/rtf"
   "sgm"   "text/sgml"
   "sgml"  "text/sgml"
   "svg"   "image/svg+xml"
   "swf"   "application/x-shockwave-flash"
   "tar"   "application/x-tar"
   "tif"   "image/tiff"
   "tiff"  "image/tiff"
   "txt"   "text/plain"
   "war"   "application/java-archive"
   "webm"  "video/webm"
   "wmv"   "video/x-ms-wmv"
   "xbm"   "image/x-xbitmap"
   "xls"   "application/vnd.ms-excel"
   "xml"   "text/xml"
   "xpm"   "image/x-xpixmap"
   "xwd"   "image/x-xwindowdump"
   "zip"   "application/zip"
   })


;;
;; API
;;

(defprotocol MIMETypeDetection
  (mime-type-of  [path] "Returns MIME type of file at given path or with given filename")
  (mime-type-for [path] "Returns MIME type for given file extension"))

(declare extension-of)

(extend-protocol MIMETypeDetection
  String
  (mime-type-of
    [^String path]
    (mime-type-of (File. path)))
  (mime-type-for
    [^String extension]
    (*mime-types* (.toLowerCase extension)))

  File
  (mime-type-of
    [^File path]
    (let [name (.getName path)
          ext  (extension-of name)]
      (mime-type-for (or ext "")))))


(defn extension-of
  "Returns the file extension of a filename or filepath"
  [filename]
  (second (re-find #"\.([^./\\]+)$" filename)))

