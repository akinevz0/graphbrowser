# README

This project aims to develop an efficient application for downloading and presenting the user a list of resources that a webpage links to on the Internet.

PagerTS is a command line utility that provides the user with a portable tool for transforming an URL into a JSON Object. The output of this command represents the navigable items within a webpage.

## Usage

To use GraphBrowser, first install PagerTS. 

### Installing PagerTS

Run it in the command line as:

```bash
cd pagerts
npm install -g ./
pagerts -h
```

It will be made available as a system-wide application under the name of `pagerts`

To run the PagerTS application from within the project folder:

```bash
npm start -- https?://...
```

### PagerTS Output

The output encodes a dependency list of resources. It is parsed using JSDOM library and returned to the user as an object annotated with fields `title`, `url`, `resources`.

The last field represents a list of tuples, mapping name of the resource to a url.

The name is extracted from the readable text on the page.

### Installing GraphBrowser

The application can be installed using:

```bash
cd graphbrowser
quarkus build
quarkus run
```

Alternatively, if Quarkus CLI is not installed:

```bash
cd graphbrowser
./gradlew clean quarkusBuild quarkusRun
```

Hint: check whether gradle is installed on the system.

## Configuring GraphBrowser

GraphBrowser configuration is located in `src/main/resources/application.properties`.

Default port can be configured: please see [quarkus documentation](https://quarkus.io/guides/http-reference)

## Running GraphBrowser

Upon first running the application, it will take a while to warm up. Please allow up to 2 minutes for the first query to return a result.

## Bugs and TODOs

There is currently no welcome page. Contributions are welcome to the `src/main/webui/src/index.md` file.