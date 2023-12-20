package builders

class Kaniko {
    static void build(
    context, 
    Boolean noPush = true,String destination,
    String bitbucketUsername, String bitbucketPassword) {
     context.sh "exit 1"
    context.sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${destination} --build-arg USERNAME=${bitbucketUsername} --build-arg PASSWORD=${bitbucketPassword} --no-push --tar-path image.tar"
    }

}