
# Angular 2

## Introduction

The new [Angular site](https://angular.io/).  

As you can notice, this is not [AngularJS](https://angularjs.org/) anymore : the framework is not a JavaScript framework anymore. You can write your application using [TypeScript](http://www.typescriptlang.org/), [EcmaScript 5](http://www.ecma-international.org/publications/files/ECMA-ST/Ecma-262.pdf), [EcmaScript 6](https://people.mozilla.org/~jorendorff/es6-draft.html) or [Dart](https://www.dartlang.org/).  

In this guide we will use [TypeScript](http://www.typescriptlang.org/) since it is a statically typed language and it is the preferred language for [Angular](https://angular.io/). Another advantage of using [TypeScript](http://www.typescriptlang.org/) is that type definitions are usually published in the [DefinitelyTyped repository](http://definitelytyped.org/).  

## Prerequisites

> Install [Node.js](https://nodejs.org/)  

The [npm](https://www.npmjs.com/) package manager is bundled with [Node.js](https://nodejs.org/).  

> Install [TSD](https://www.npmjs.com/package/tsd)

[TSD](https://www.npmjs.com/package/tsd) is a package manager to search and install [TypeScript](http://www.typescriptlang.org/) definition files directly from the [DefinitelyTyped repository](http://definitelytyped.org/).  

	sudo npm install -g tsd

> Install the [TypeScript compiler](http://www.typescriptlang.org/)

Since the browser doesn't understand [TypeScript](http://www.typescriptlang.org/) code, we need to run a compiler to translate your code to browser-compliant JavaScript.  

	sudo npm install -g typescript

## Step 1

> Create a folder named `step-1`.  

	mkdir step-1

From now on, all commands are run fromt the `step-1` directory unless otherwise mentioned.  

> Create the html and [TypeScript](http://www.typescriptlang.org/) file

	touch app.ts index.html

As you can see, [TypeScript](http://www.typescriptlang.org/) files have a `ts` extension.  

> Instruct the [TypeScript compiler](http://www.typescriptlang.org/) to automatically convert the `ts` files to [EcmaScript 5](http://www.ecma-international.org/publications/files/ECMA-ST/Ecma-262.pdf) files

	tsc --watch -m commonjs -t es5 --emitDecoratorMetadata app.ts

This instructs the compiler to listen for any changes to the `app.ts` file and generate a new `app.js` file each time a change is made.  

> Install Angular2 definitions

	tsd query angular2 --action install

## Dependencies

If a class depends on another class, the second class needs to be declared before the first one :  

	class FriendsService {

		names: Array<string>;

		constructor() {
			this.names = ["Alice", "Aarav", "Martín", "Shannon", "Ariana", "Kai"];
		}

	}

	class DisplayComponent {

		myName: string;
		names: Array<string>;

		constructor(friendsService: FriendsService) {
			this.myName = "Alice";
			this.names = friendsService.names;
		}

	}

If `FriendsService` is declared after `DisplayComponent`, you get an error !  


## Multi Directory

> Watch and convert

	find . -name "*.ts" | xargs tsc --watch -m commonjs -t es5 --emitDecoratorMetadata

# Resources

1. [Angular's 5 MIN QUICKSTART](https://angular.io/docs/js/latest/quickstart.html)
