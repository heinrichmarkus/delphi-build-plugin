package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.tasks.base.DefaultWriteConstantTask;
import de.heinrichmarkus.gradle.delphi.utils.GitCommitReader;

import java.io.File;

public class WriteCommitTask extends DefaultWriteConstantTask {
    @Override
    protected String calculateValue() {
        return GitCommitReader.readCommit(new File(".git/HEAD"));
    }
}
