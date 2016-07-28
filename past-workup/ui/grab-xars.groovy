/*
 * This file is subject to the terms and conditions defined in file LICENSE,
 * which is part of this source code package.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
println "TESTING NIAID HAHAHAHAHAHA"


import groovy.io.FileType

def list = []

def dir = new File("./")
dir.eachFileRecurse (FileType.FILES) { file ->
    list << file
}

println list

def url = 'http://localhost:8080/export/PhenoTips/UIX_Field__past-workup?format=xar&name=PhenoTips.UIX_Field__past-workup&pages=xwiki%3APhenoTips.UIX_Field__past-workup'
def file = new File('target/test.xar').newOutputStream()
URLConnection urlConnection = new URL(url).openConnection();
String userCredentials = "Admin:admin";
String basicAuth = "Basic " + new String(userCredentials.bytes.encodeBase64().toString());
urlConnection.setRequestProperty ("Authorization", basicAuth);
file << urlConnection.getContent();



