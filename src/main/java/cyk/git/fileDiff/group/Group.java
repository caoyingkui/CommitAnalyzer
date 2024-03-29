package cyk.git.fileDiff.group;

import cyk.git.fileDiff.method.MethodDiff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kvirus on 2019/5/27 17:24
 * Email @ caoyingkui@pku.edu.cn
 * <p>
 * |   *******    **      **     **     **
 * |  **            **  **       **  **
 * |  **              **         ***
 * |  **              **         **  **
 * |   *******        **         **     **
 */
public class Group {
    int hash;
    public Set<String> keyWords = new HashSet<>();
    private List<MethodDiff> methods = new ArrayList<>();

    public void addMethod(MethodDiff method) {
        try {
            if (keyWords == null)
                keyWords = new HashSet<>();
            methods.add(method);
            keyWords.addAll(method.changedWords);
            keyWords.addAll(method.delWords);
            keyWords.addAll(method.addWords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MethodDiff> getMethods() {
        return methods;
    }

    public Group(int hash, HashSet<String> keyWords) {
        this.hash = hash;
        this.keyWords = keyWords;
    }
}
