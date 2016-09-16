# Android Sensor App - README

## Purpose

While the main data collection source offered by the workshop are the iPhones located in the elevators, we wanted 
to offer android device holders and developers an opportunity to customize their data collection setup to their liking by providing
an example Sensor app for android, which functions in a similar fashion to its iOS counterpart. You can use this project in two different
ways: simply install the app on your device and start gathering data (you'll still need Android Studio to install it), or download the code 
and modify it as you please.

## Data Ouput

Here are some details about the data output format for the individual sensors. All 3D sensor data points are given in a coordinate system that is set relative to screen orientation. For more details check:

https://developer.android.com/reference/android/hardware/SensorEvent.html

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

### Gyroscope

````
{
  "type" : "Gyroscope"
  "date" : "2016-09-11T10:05:33.002-0200",
  "x" : "0.2333",
  "y" : "2.1345",
  "z" : "1.0225",
}
````
* `"x"`: Angular speed around the x-axis
* `"y"`: Angular speed around the y-axis
* `"z"`: Angular speed around the z-axis

From the android developer docs:

All values are in radians/second and measure the rate of rotation around the device's local X, Y and Z axis. The coordinate system is the same as is used for the acceleration sensor. Rotation is positive in the counter-clockwise direction. That is, an observer looking from some positive location on the x, y or z axis at a device positioned on the origin would report positive rotation if the device appeared to be rotating counter clockwise.

### Light Sensor

````
{
  "type" : "Light"
  "date" : "2016-09-12T11:04:00.002-0200",
  "level" : "345.7",
}
````
* `"level"`: Ambient light level in SI lux units

### Linear Acceleration

````
{
  "type" : "Linear_acceleration"
  "date" : "2016-09-12T11:17:03.002-0200",
  "x" : "-0.0066",
  "y" : "-0.0363",
  "z" : "-1.0032",
}
````
All values are in SI units (m/s^2)

From the android documentation:

A three dimensional vector indicating acceleration along each device axis, not including gravity. All values have units of m/s^2. The coordinate system is the same as is used by the acceleration sensor.

### Temperature

````
{
  "type" : "Temperature"
  "date" : "2016-09-12T10:04:33.002-0200",
  "temperature" : "22.3",
}
````
* `"temperature"`: ambient (room) temperature in degree Celsius.

### Step counter

````
{
  "type" : "Temperature"
  "date" : "2016-09-12T10:04:33.002-0200",
  "stepsSinceLastReboot" : "1023",
}
````
* `"stepsSinceLastReboot"`: number of steps taken by the user since the last reboot.

From the android documentation:

A constant describing a step counter sensor.

A sensor of this type returns the number of steps taken by the user since the last reboot while activated.

## Sensors API

For more information on accessing the android sensors api, check the official documentation at:

https://developer.android.com/guide/topics/sensors/sensors_overview.html

and

https://developer.android.com/guide/topics/sensors/sensors_environment.html






