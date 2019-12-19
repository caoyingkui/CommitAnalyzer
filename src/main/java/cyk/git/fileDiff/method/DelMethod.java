package cyk.git.fileDiff.method;

import cyk.git.analyzer.histories.Comment;
import cyk.git.fileDiff.Diff;
import cyk.git.fileDiff.file.FileDiff;
import cyk.git.fileDiff.type.DiffType;
import cyk.git.git.Method;

import java.util.HashSet;

/**
 * Created by kvirus on 2019/4/20 17:16
 * Email @ caoyingkui@pku.edu.cn
 * <p>
 * |   *******    **      **     **     **
 * |  **            **  **       **  **
 * |  **              **         ***
 * |  **              **         **  **
 * |   *******        **         **     **
 */
public class DelMethod extends MethodDiff implements MethodUpdate, FieldUpdate{
    public DiffType type;

    public String name;

    public String fullName;

    public String signature;

    public String content;

    public String comment;

    public HashSet<String> tokens;

    @Override
    public int commentWords(Comment comment) {
        int count = 0;
        String content = comment.content;
        for (String token: tokens)
            if (content.contains(token))
                count ++;
        return count;
    }

    @Override
    public int commonKeyWords(Comment comment) {
        int count = 0;
        String content = comment.content;

        if (comment.content.contains(name))
            count += 5;

        for (String token: changedWords) {
            if (content.contains(token)) count ++;
        }

        for (String token: delWords) {
            if (content.contains(token)) count ++;
        }
        return count;
    }

    public DelMethod(Method method, FileDiff file, String commitId) {
        this.commitId = commitId;

        name = method.name;
        fullName = method.fullName;
        signature = getSignature(method.fullName);
        content = method.methodContent;
        comment = method.comment;
        this.file = file;

        extractChangedTokens();
        getType();
    }

    @Override
    public void extractChangedTokens() {
        tokens = MethodDiff.extractTokens(content);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void parseInterfaceRelation(MethodDiff method) {

    }

    @Override
    public void parseRefactorRelation(MethodDiff method) {

    }

    @Override
    public void fieldUpdate(Diff diff) {
        for (String token: diff.delFields.keySet())
            if (tokens.contains(token))
                delWords.add(token);

        for (String token: diff.delMethods.keySet())
            if (tokens.contains(token))
                delWords.add(token);

        for (String token: diff.changedMethods.keySet())
            if (tokens.contains(token))
                delWords.add(token);
    }

    @Override
    public void update(Diff diff) {

    }

    @Override
    public String toString() {
        return name;
    }
}
