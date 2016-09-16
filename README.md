# Android Sensor App - README

## Purpose

While the main data collection source offered by the workshop are the iPhones located in the elevators, we wanted 
to offer android device holders and developers an opportunity to customize their data collection setup to their liking by providing
an example Sensor app for android, which functions in a similar fashion to its iOS counterpart. You can use this project in two different
ways: simply install the app on your device and start gathering data (you'll still need Android Studio to install it), or download the code 
and modify it as you please.

## Data Ouput

Here are some details about the data output format for the individual sensors. All 3D sensor data points are given in a coordinate system that is set relative to screen orientation. For more details check:

https://developer.android.com/reference/android/hardware/SensorEvent.html#values

### Barometer

````
{
  "type" : "Barometer"
  "date" : "2016-09-10T12:33:18.413-0200",
  "pressure" : "1013.564",
}
````
`"pressure"` : Atmospheric pressure in hPa (millibar)

### Accelerometer

````
{
  "type" : "Accelerometer"
  "date" : "2016-09-12T13:43:08.322-0200",
  "x" : "-0.0066",
  "y" : "-0.0363",
  "z" : "-1.0032",
}
````
All values are in SI units (m/s^2)
* `"x"`: Acceleration minus Gx on the x-axis
* `"y"`: Acceleration minus Gy on the y-axis
* `"z"`: Acceleration minus Gz on the z-axis

### Magnetometer

````
{
  "type" : "Magnetometer"
  "date" : "2016-09-13T08:03:02.142-0200",
  "x" : "0.008",
  "y" : "0.0345",
  "z" : "0.045",
}
````
All values are in micro-Tesla (uT)
* `"x"`: Ambient magnetic field in the X axis.
* `"y"`: Ambient magnetic field in the Y axis.
* `"z"`: Ambient magnetic field in the Z axis.






