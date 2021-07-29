package a.f.utils.filefilter;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 类名：a.f.utils.filefilter
 * 时间：2021/7/20 17:37
 * 描述：删除文件 过滤器
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DeleteFileFilter implements IOFileFilter, Serializable {

    private List<String> files;

    public DeleteFileFilter() {
    }

    public DeleteFileFilter(List<String> files) {
        this.files = files;
    }

    @Override
    public boolean accept(File file) {
        new Thread(() -> {
            try {
                String path = file.getAbsolutePath();

                if (files == null) {
                    if (file.isFile()) {
                        file.delete();
                    }
                } else {
                    if (file.isFile() && files.contains(path)) {
                        file.delete();
                    }
                }
            } catch (Exception e) {
            }
        }).start();

        return false;
    }

    @Override
    public boolean accept(File dir, String name) {
        return false;
    }

}
