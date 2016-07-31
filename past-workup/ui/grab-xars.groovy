/*
 * This file is subject to the terms and conditions defined in file LICENSE,
 * which is part of this source code package.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
import groovy.io.FileType
import java.io.IOException;

println "[INFO] ------------------------------------------------------------------------"
println "[INFO] Importing UI XML's from local Phenotips instance"
println "[INFO] ------------------------------------------------------------------------"
println "[INFO]"
println "[INFO] --- basedir:" + basedir
println "[INFO]"

def fileResult = this.getUIFiles(new File((String)basedir))

println "fileResult=" + fileResult

def url = 'http://localhost:8080/export/PhenoTips/UIX_Field__past-workup?format=xar&name=PhenoTips.UIX_Field__past-workup&pages=xwiki%3APhenoTips.UIX_Field__past-workup'

URLConnection urlConnection;

try {
    urlConnection = new URL(url).openConnection();
    urlConnection.connect()
}
catch (IOException e) {
    println "[INFO] ------------------------------------------------------------------------"
    println "[WARN] Could not connect to localhost Phenotips instance, skipping ..."
    println "[INFO] ------------------------------------------------------------------------"
    println "[INFO]"
    return;
}

def file = new File('target/test.xar').newOutputStream()
String userCredentials = "Admin:admin";
String basicAuth = "Basic " + new String(userCredentials.bytes.encodeBase64().toString());
urlConnection.setRequestProperty ("Authorization", basicAuth);
file << urlConnection.getContent();

LinkedList getUIFiles(basedir) {

    LinkedList fileList = [] as LinkedList

    println "[INFO] getUIFiles(), basedir=" + ((File)basedir).getName()

    if ("ui".equals(((File)basedir).getName())) {
        this.getFilesFromUIDir(basedir, fileList)
    }
    else {
        basedir.eachDirRecurse() { dir ->
            if ("ui".equals(((File)dir).getName())) {
                this.getFilesFromUIDir(dir, fileList)
            }
        }
    }

    return fileList
}

void getFilesFromUIDir(uiDir, LinkedList fileList) {
    uiDir.eachDirRecurse() { dir ->
        if ("resources".equals(((File)dir).getName())) {
            dir.eachFileRecurse() { file ->
                if ((((File)file).getName()) ==~ /.*.xml$/) {
                    fileList << file
                }
            }
        }
    }
}
