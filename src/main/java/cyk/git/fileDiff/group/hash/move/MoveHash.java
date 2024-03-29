package cyk.git.fileDiff.group.hash.move;

import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import cyk.git.fileDiff.group.hash.StatementHash;

/**
 * Created by kvirus on 2019/6/15 22:06
 * Email @ caoyingkui@pku.edu.cn
 * <p>
 * |   *******    **      **     **     **
 * |  **            **  **       **  **
 * |  **              **         ***
 * |  **              **         **  **
 * |   *******        **         **     **
 */
public class MoveHash extends StatementHash{
    public static MoveHash getHash(SourceCodeChange change) {
        return new MMove(change);
    }
}
