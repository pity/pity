package io.ask.tasks.collector
import io.ask.api.EnvironmentCollector
import io.ask.api.EnvironmentData
import io.ask.api.EnvironmentDataBuilder
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.ObjectReader
import org.eclipse.jgit.lib.RepositoryBuilder
import org.eclipse.jgit.treewalk.CanonicalTreeParser

class GitEnvironmentCollector implements EnvironmentCollector{

    @Override
    EnvironmentData collect(File workingDirectory) {
        def builder = EnvironmentDataBuilder.Builder(GitEnvironmentCollector.class.getSimpleName())


        def repositoryBuilder = new RepositoryBuilder().findGitDir()
        if(repositoryBuilder.getGitDir() == null){
            return builder.build();
        }

        def repository = repositoryBuilder.build()
        ObjectId head = repository.resolve("HEAD^{tree}");
        ObjectId master = repository.resolve("origin/master^{tree}");

        builder.addData('head', head.getName()).addData('master', master?.getName())

        if(null == master){
            return builder.build()
        }

        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        oldTreeIter.reset(reader, master);
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        newTreeIter.reset(reader, head);

        // finally get the list of changed files
        List<DiffEntry> diffs= new Git(repository).diff()
                .setNewTree(newTreeIter)
                .setOldTree(oldTreeIter)
                .call();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        def df = new DiffFormatter(out)
        df.format(diffs)

        builder.addData('path', out.toString('UTF-8'))

        return builder.build()
    }
}
