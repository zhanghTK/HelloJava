package tk.zhangh.java.x.server;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * web.xml mapping
 */
@Data
public class Mapping {
    private String name;
    private List<String> urlPattern = new ArrayList<>();
}
