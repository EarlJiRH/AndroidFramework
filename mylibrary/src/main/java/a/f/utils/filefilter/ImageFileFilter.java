package a.f.utils.filefilter;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.Serializable;

import a.f.utils.AFImageUtils;

/**
 * ================================================
 * 类名：a.f.utils.filefilter
 * 时间：2021/7/20 17:38
 * 描述：图片过滤
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ImageFileFilter implements IOFileFilter, Serializable {

    @Override
    public boolean accept(File file) {
        return AFImageUtils.getInstance().isImageFormat(file.getAbsolutePath());
    }

    @Override
    public boolean accept(File dir, String name) {
        return false;
    }
}
