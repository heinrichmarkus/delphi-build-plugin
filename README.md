# Delphi Build Plugin

A Gradle Plugin to build Delphi Projects. This plugin was written for build automation with Jenkins on Windows. It's tested with Delphi 10.2 Tokyo but it should also work with earlier versions.

Requirements:
* `Gradle 4.3+`
* `Java 8+`
* `Delphi 10.2+`

## Features

* Update all Project-Files (*.dproj) with the project version
* Assign the project version to a constant
* Assign the current git commit hash to a constant
* Compile the project (Win32, Win64, Android, iOS)
* Execute Unit-Tests
* Create a ZIP-Archive for delivery

## Project Structure

The plugin is designed to work with the following project structure:

```
.git
\bin
\src
build.gradle
```

Although this is not the default Delphi project structure I recommend this to clearly separate source files from generated files. The second thing is that the plugin's `clean` and `assemble` tasks need a single directory that contains all generated files. To accomplish this you need to update the project settings with this output dir: `..\bin\$(Platform)\$(Config)` (instead of `.\$(Platform)\$(Config)`)

## Tasks

### Build
* assemble
* brand - Brand the version and the commit hash to constants in your source files. You can use these constants in your source code without the need of manually updating them.

```
const
  PROGRAM_VERSION = '<version name will be inserted here>'; // e.g. '0.0.1-2018-03-10_143731';
  COMMIT_HASH = '<commit hash will be inserted here>'; // e.g. '785955d40d60602458dfcf5b0ed0d718be3997e5';
```

* build
* clean - Delete binary files
* compile
* test

### Util
* writeCommit - Write commit id to project files
* writeProjectVersion - Write version to project files
* writeVersionConstant - Write version to a constant in source code

### Info
* check - Check project configuration
* listCompilers - Display list of Delphi installations
* readCommit - Display commit id from source-file
* readVersion - Display version from source-file

## Configuration

Here's an example for a simple *build.gradle* file.

```
plugins {
  id "de.heinrichmarkus.gradle.delphi" version "0.2.0"
}

project {
	bin = 'bin/'
	bds = '19.0'
	compiler.add('src/Project/PGradleDemo_App.dproj', 'Release', 'Win32')
	compiler.add('src/Project/PGradleDemo_Test.dproj', 'Release', 'Win32')
	test.add('bin/Win32/Release/PGradleDemo_Test.exe', '-exit:continue')
	name = 'GradleDemoApplication'
	version = '0.0.1'
	versionCode = 19
	versionConstantFile = 'src/Units/Constants.pas'
	versionConstantName = 'PROGRAM_VERSION'
	commitConstantFile = versionConstantFile
	commitConstantName = 'COMMIT_HASH'
	noBrand = false
	noVersionDate = false
	assembly.add('bin/Win32/Release/PGradleDemo_Client.exe')
}

```

Setting | Description | Default
--------|-------------|---------
bin     | Directory that contains all generated files. | bin/
bds     | Target Delphi Version. | 
compiler | List of projects to compile. You can add the same projects multiple times for different platforms. |
test    | List of test executables to run. Passes if the return code is 0. |
name    | Project Name. Used as part of the ZIP-Archive's name. |
version | Version Number. |
versionCode | Version for Android Apps. |
versionConstantFile | File that contains the version constant. |
versionConstantName | Name of the version constant. |
commitConstantFile | File that contains the commit hash constant. |
commitConstantName| Name of the commit hash constant. |
noBrand | Neither update version constant and commit constant nor write version to project files. | false
noVersionDate | Don't append version constant with the build date (e.g. 0.0.1-2018-03-10_143731). | false
assembly | List of files to be added the output ZIP-Archive. You can simply skip this if you don't want a ZIP-Archive to be created. |

### BDS Versions

BDS Version | Product
------------|--------
8  | Delphi XE
9  | Delphi XE2
10 | Delphi XE3
11 | Delphi XE4
12 | Delphi XE5
14 | Delphi XE6
15 | Delphi XE7
16 | Delphi XE8
17 | Delphi 10 Seattle
18 | Delphi 10.1 Berlin
19 | Delphi 10.2 Tokyo

### Compile

You have to specify which project should be compiled for what platform. Configure as many items as you need. The function `compiler.add()` supports the following parameters:

* add(String file, String config, String platform)
* add(String file, String config, String platform, String target)

Here are some examples:

Example | Description
--------|------------
compiler.add('src/Project1.dproj', 'Release', 'Win32') | Compile for Win32
compiler.add('src/Project1.dproj', 'Release', 'Android') | Compile for Android
compiler.add('src/Project1.dproj', 'Release', 'Android', 'Deploy') | Additional step for the provision of an app
compiler.add('src/Project1.dproj', 'Release', 'iOSDevice64') | Compile for iOS
compiler.add('src/Project1.dproj', 'Release', 'iOSDevice64', 'Deploy') | Additional step for the provision of an app

The plugin will compile your project with msbuild.

### Assembly

Assemble a ZIP-Archive with any file or directory you need for delivery. The function `assembly.add()` supports the following parameters:

* add(String source)
* add(String source, Boolean optional)
* add(String source, String destination)
* add(String source, String destination, Boolean optional)

`source` can be a file or a directory. `destination` determines a subdirectory and a different filename. You can mark a file as `optinal`. Your build will then not fail if this file doesn't exist.

Here are some examples:

Example | Description
--------|------------
assembly.add('bin/Win32/Release/Project1.exe') | Add file Project1.exe
assembly.add('bin/Win32/Release/Project1.exe', 'Application.exe') | Add file and rename it to Application.exe
assembly.add('bin/Win32/Release/Project1.exe', 'bin/') | Add file to the directory bin/
assembly.add('bin/Win32/Release/Project1.exe', 'bin/Application.exe') | Add file to directory bin/ an rename it to Application.exe
assembly.add('src/Resources') | Add the directory's content
