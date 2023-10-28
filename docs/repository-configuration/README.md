# Repository Configuration

This section guides you on how this repository was setup

## Managing the automatic deletion of branches
You can have head branches automatically deleted after pull requests are merged in your repository.

See steps on [Managing the automatic deletion of branches](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/configuring-pull-request-merges/managing-the-automatic-deletion-of-branches)

## Code Analysis/Scanning
### Configuring repository for GitHub CodeQL
[CodeQL](https://codeql.github.com/docs/codeql-overview/about-codeql/) is the analysis engine used by developers to automate security checks, and by security researchers to perform variant analysis.

In CodeQL, code is treated like data. Security vulnerabilities, bugs, and other errors are modeled as queries that can be executed against databases extracted from code. You can run the standard CodeQL queries, written by GitHub researchers and community contributors, or write your own to use in custom analyses. Queries that find potential bugs highlight the result directly in the source file.

See steps on [Configuring default setup for a repository](https://docs.github.com/en/code-security/code-scanning/enabling-code-scanning/configuring-default-setup-for-code-scanning#configuring-default-setup-for-a-repository)
