package cn.hsa.util;

import cn.hutool.core.codec.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: GenerateQRCodeUtil
 * @Describe: 生成base64二维码工具类
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/10/09 13:42
 * @Company: CopyRight@2022 POWERSI Inc.All Rights Reserverd
 */
public class GenerateQRCodeUtils {
    private static Logger logger = LoggerFactory.getLogger("GenerateQRCodeUtils");
    // 二维码需要使用到的颜色
    private static int BLACK = 0xFF000000;
    private static int GREEN = 0x006C01;
    private static int YELLOW = 0xFF8D00;
    private static int WHITE = 0xFFFFFFFF;

//    public static void main(String[] args) {
//        GenerateQRCodeUtils generateQrCodeUtils = new GenerateQRCodeUtils();
//        generateQrCodeUtils.createQrCodeImg("",150,150);
//    }

    public static String createQrCodeImg(String encodeContent,int pWidth,int pHeight) {
        // 需要生成的二维码的文字、地址
//        String GQrCodeStr = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx157e11711717b8fb&redirect_uri=http%3A%2F%2Fzsyy.hnnkyy.com%2FmobileHospitalService%2FwxMh%2FwxOAuthAction.action%3FmenuCode%3DtoXghsjyByGreen&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//        String YQrCodeStr = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx157e11711717b8fb&redirect_uri=http%3A%2F%2Fzsyy.hnnkyy.com%2FmobileHospitalService%2FwxMh%2FwxOAuthAction.action%3FmenuCode%3DtoXghsjy&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//        // 生成的文件路径，这样写是生成在桌面，如果需要在另外的路径直接把文件路径替换即可。
//        String imgPath = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator;
//        // 生成的二维码名称
//        String YimgName = "YellowCode.jpg";
//        String GimgName = "GreenCode.jpg";
        // 创建二维码
        try {
            Map<EncodeHintType, String> charcter = new HashMap<>();
            // 设置字符集
            charcter.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的四个参数   需要生成的字符串，类型设置为二维码，二维码宽度，二维码高度，字符串字符集
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(encodeContent, BarcodeFormat.QR_CODE, pWidth, pHeight, charcter);
            // 创建文件对象
            // File file = new File(imgPath, YimgName);
            // 二维码像素，也就是上面设置的 150X150
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            // 创建二维码对象
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // 按照上面定义好的二维码颜色编码生成二维码
                    image.setRGB(x, y, bitMatrix.get(x, y) ? GREEN : WHITE);
                }
            }
            // 1、第一种方式
            // 生成的二维码图片对象转 base64
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // 设置图片的格式
            ImageIO.write(image, "png", stream);
            String base64 = Base64.encode(stream.toByteArray());
            // 输出转换成功后的base64编码
            logger.info(base64);
            // 2、第二种方式
            // 直接输出二维码文件
            // ImageIO.write(image, "jpg", file);
            //System.out.println("二维码创建成功文件地址：" + file);
            //System.out.println("二维码文件路径：" + imgPath);
            return base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
