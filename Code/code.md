#Coding - Technical Considerations taken while building this app.

- The app was built with the focus that everything should be seperated, which makes it easier to debug.
- Material Design library was used to implement material components.
- "Retrofit2" was used to implement the network calls. It performs faster and makes life easy with models.
- "Picasso" was used to handle image loading.
- "Proxy Pattern" was used while performing network operations, so that all the network actions are in a different file and data is exchanged using an interface. 
- Error conditions were handled such as checking internet connection before making an API request.

## Optimizations performed

###Reducing Overdraw
Hey, no one likes glitchy apps.<br>
The app was built in such a way so that it has minimal overdraw, so the app would run smoothly even on lower end devices. 

####Before - Red Areas(Not Good)
![alt text](https://github.com/maitray16/ILoveZappos/blob/master/Design/Resources/before.png?raw=true)

####After - Blue and Green(Good)
![alt text](https://github.com/maitray16/ILoveZappos/blob/master/Design/Resources/after.png?raw=true)


### Reducing the apk size

By optimising resources and writing the pro-guard rules for the libraries that is Retrofit, I was able to reduce the apk size as it removes the portion of the code that is never used. People are always running out of memory space, so I feel the lesser the apk size the better it is.
