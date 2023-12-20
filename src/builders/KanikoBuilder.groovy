package builders

class KanikoBuilder {

    static void build(context) {
  
       context.sh(
            returnStdout: true,
            script: 'ls -a'
       ) 
       
    //    "set +x && /kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${containerRegistry} --build-arg USERNAME=\$BITBUCKET_USERNAME --build-arg PASSWORD=\$BITBUCKET_PASSWORD --no-push --tar-path image.tar"

    }
}
