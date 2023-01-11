package com.intersem.sdib.core.utilities;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

public class FTP {
    private FTPClient ftpClient;
    private boolean result = false;

    public boolean verifyConnect() throws IOException {
        ftpClient = new FTPClient();

        //
        ftpClient.connect(InetAddress.getByName("192.168.100.5"));
        result = ftpClient.login("icssys_api", "ZJj^^gm%TBYrU");

        return result;
    }

    public boolean uploadFile(String fileRoute) throws IOException {
        ftpClient = new FTPClient();

        //
        ftpClient.connect(InetAddress.getByName("192.168.100.5"));
        ftpClient.login("icssys_api", "ZJj^^gm%TBYrU");
        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();

        //
        BufferedInputStream buffIn = null;
        buffIn = new BufferedInputStream(new FileInputStream(fileRoute));
        result = ftpClient.storeFile("3.mp3", buffIn);

        //
        buffIn.close();
        ftpClient.logout();
        ftpClient.disconnect();

        return result;
    }

    /*
    public boolean uploadFile(ResponseSessionModel.User.Ftp ftp, String ftp_path, String name, String file_path) throws IOException {
        ftpClient = new FTPClient();

        //
        ftpClient.connect(InetAddress.getByName(ftp.getServer_ftp()));
        ftpClient.login(ftp.getUser_ftp(), ftp.getPassword_ftp());
        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();

        //
        ftpClient.makeDirectory(ftp_path);

        //
        BufferedInputStream buffIn = null;
        buffIn = new BufferedInputStream(new FileInputStream(file_path));
        result = ftpClient.storeFile(ftp_path + name, buffIn);

        //
        buffIn.close();
        ftpClient.logout();
        ftpClient.disconnect();

        return result;
    }

     */

    /*
    public boolean downloadFile(ResponseSessionModel.User.Ftp ftp, String remotePath, String localFolder, String nameFile) throws IOException{
        ftpClient = new FTPClient();

        //
        ftpClient.connect(InetAddress.getByName(ftp.getServer_ftp()));
        ftpClient.login(ftp.getUser_ftp(), ftp.getPassword_ftp());
        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();

        //
        File file = new File(localFolder);
        if(!file.exists()) file.mkdirs();
        result = ftpClient.retrieveFile(remotePath, new FileOutputStream(file.getPath() + "/" + nameFile));

        //
        ftpClient.logout();
        ftpClient.disconnect();

        return result;
    }

     */
}
