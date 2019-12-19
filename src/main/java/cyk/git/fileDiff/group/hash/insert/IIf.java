package cyk.git.fileDiff.group.hash.insert;

import ch.uzh.ifi.seal.changedistiller.model.classifiers.java.JavaEntityType;
import ch.uzh.ifi.seal.changedistiller.model.entities.Delete;
import ch.uzh.ifi.seal.changedistiller.model.entities.Insert;
import ch.uzh.ifi.seal.changedistiller.model.entities.Move;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import cyk.git.fileDiff.group.hash.StatementHash;
import cyk.git.fileDiff.group.hash.visitor.RenameVisitor;
import cyk.git.util.CompileTool;
import org.eclipse.jdt.core.dom.*;

/**
 * Created by kvirus on 2019/6/16 13:28
 * Email @ caoyingkui@pku.edu.cn
 * <p>
 * |   *******    **      **     **     **
 * |  **            **  **       **  **
 * |  **              **         ***
 * |  **              **         **  **
 * |   *******        **         **     **
 */
public class IIf extends InsertHash{
    public final int ACTION     = 0;
    public final int STATEMENT  = 1;
    public final int PARENT     = 2;
    public final int CONDITION  = 3;

    public IIf(SourceCodeChange change) {
        super(change);
        assert (change instanceof Insert || change instanceof Delete || change instanceof Move)&&
                ( change.getChangedEntity().getType() == JavaEntityType.IF_STATEMENT ||
                    change.getChangedEntity().getType() == JavaEntityType.ELSE_STATEMENT) ;

        hashes = new int[4];

        hashes[ACTION]      = typeHash(change);
        hashes[STATEMENT]   = statementHash();
        hashes[PARENT]      = blockStatementHash(change.getParentEntity().getType());
        hashes[CONDITION]   = getConditionHash(change.getChangedEntity().getUniqueName());
    }

    private int statementHash() {
        return getCode(ASTNode.IF_STATEMENT);
    }

    private int getConditionHash(String conditionExpression) {
        Expression expression = CompileTool.getExpression(conditionExpression);

        String s = "";
        if (expression instanceof MethodInvocation) {
            s = ((MethodInvocation)expression).getName().toString();
        } else if (expression instanceof SimpleName){
            s = conditionExpression;
        } else {
            RenameVisitor visitor = new RenameVisitor();
            expression.accept(visitor);
            s = expression.toString();
        }
        return s.hashCode();
    }

    @Override
    public boolean equals(StatementHash hash) {
        if (!super.equals(hash)) return false;

        if (strict) {
            int len = hashes.length;
            for (int i = 2; i < len; i ++)
                if (hashes[i] != hash.hashes[i]) return false;
            return true;
        } else {
            return hashes[CONDITION] == hash.hashes[CONDITION];
            //return true;
        }
    }
}
