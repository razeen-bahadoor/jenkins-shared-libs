package builders

import jenkins.Steps

static void build() {
    Steps steps = new Steps()
    steps.echo "hello"
    //    "set +x && /kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${containerRegistry} --build-arg USERNAME=\$BITBUCKET_USERNAME --build-arg PASSWORD=\$BITBUCKET_PASSWORD --no-push --tar-path image.tar"

}
