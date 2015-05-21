ParallaxListView
================

##Issues

[![Stories ready to be worked on](https://badge.waffle.io/barriosnahuel/parallaxListView.png?label=ready&title=Ready)](https://waffle.io/barriosnahuel/parallaxListView) [![Stories in progress](https://badge.waffle.io/barriosnahuel/parallaxListView.png?label=in progress&title=In Progress)](https://waffle.io/barriosnahuel/parallaxListView)

##Demo

A  ListView demo with a [parallax effect](http://en.wikipedia.org/wiki/Parallax) header like [Path](https://path.com/).


<img src="img_sample.gif" height=300>

The key of the effect is :
          android:scaleType="centerCrop"

request:
          android:minSdkVersion="9"

##How to include it in your project?

First of all you have to download the AAR file, then add the container directory as a repository by adding the following code block inside your `depdendencies` section:

    repositories {
        flatDir {
            dirs '../aars'
        }
    }

After that, just add the library with all its required libraries inside:

    compile('com.barriosnahuel:expandonscroll:1.0@aar') {
        compile 'com.github.bumptech.glide:glide:3.4.0'
    }

That's all!
