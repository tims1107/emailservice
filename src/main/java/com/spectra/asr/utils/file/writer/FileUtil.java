package com.spectra.asr.utils.file.writer;

import org.apache.commons.beanutils.PropertyUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {



    private static void closeChannel(Channel channel){
        if (channel != null) try{channel.close();} catch (IOException ioe){}
    }

    public static String getMSHControlNum(){
        long seed = 0;
        StringBuffer sb = new StringBuffer();
        StringBuffer sb_seed = new StringBuffer();

        //long seed = Long.parseLong(getControlNum("./target/classes/controlnum"));

        sb_seed.append(getControlNum("./target/classes/controlnum").trim());
        seed = Long.parseLong(sb_seed.toString().trim());

        if(seed == 999)  seed = 1;

        sb.append(_getControlWithDateHL7(seed));


        //Long.parseLong(sb.toString().trim());
        sb_seed.setLength(0);

        seed++;



        sb_seed.append(seed);
        fileChannelWrite(strbuf_to_bb(sb_seed),"./target/classes/controlnum",false);

        return sb.toString();
    }

    private static String _getControlWithDateHL7(long seed){

        String datenum = null;
        StringBuffer sb = new StringBuffer();
        long newnum = 0;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            datenum = sdf.format(new Date()) + "000";
        } catch (Exception e){
            e.printStackTrace();
        }
        newnum = Long.parseLong(datenum) + seed;

        return Long.toString(newnum);

    }

    private static String getControlNum(String filestr)  {
        SeekableByteChannel byteChannel = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        Path newfile = Paths.get(filestr);


//        File newfilestr = new File("./target/classes/");
//        for(String filenames : newfilestr.list()) //System.out.println(filenames);


        //Charset charset = Charset.forName("US-ASCII");
        long controlnum = 0;
        String num = null;

        try {
//            if(newfilestr.exists()) {
//                //System.out.println("File exists " + newfile);
//            } else {
//                //System.out.println("No File " + newfile);
//            }
            byteChannel = Files.newByteChannel(newfile);

            if (byteChannel.read(byteBuffer) > 0) {
                byteBuffer.rewind();

              num = bb_to_str(byteBuffer,charset);
                byteBuffer.flip();
            }
            return num;



        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeChannel(byteChannel);
        }

        return null;
    }

    public static void fileChannelWrite(ByteBuffer byteBuffer, String updateFile,boolean append)
             {

        Set options = new HashSet();
        options.add(StandardOpenOption.CREATE);
        if (append) {
            options.add(StandardOpenOption.APPEND);
        } else {
            options.add(StandardOpenOption.WRITE);
        }

        Path path = Paths.get(updateFile);

        FileChannel fileChannel = null;

        try {
            fileChannel = FileChannel.open(path, options);
            fileChannel.write(byteBuffer);

            fileChannel.close();
            fileChannel = null;
        } catch (IOException ioe){

        } finally {
            closeChannel(fileChannel);
        }



    }

    public static final Charset charset = Charset.forName("UTF-8");
    public static final CharsetEncoder encoder = charset.newEncoder();
    public static final CharsetDecoder decoder = charset.newDecoder();




    public static ByteBuffer str_to_bb(String msg, Charset charset){
        return ByteBuffer.wrap(msg.getBytes(charset));
    }

    public static ByteBuffer strbuf_to_bb(StringBuffer sb) {
        CharBuffer buffer = CharBuffer.wrap(sb);
        ByteBuffer bytes = null;
        try {
            bytes = encoder.encode(buffer);
        } catch (CharacterCodingException cce){

        }
        return bytes;
    }

    public static String bb_to_str(ByteBuffer buffer, Charset charset){
        byte[] bytes;
        if(buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }

}
