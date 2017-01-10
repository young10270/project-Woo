#ifndef HX711_h
#define HX711_h

#if ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif

class HX711
{
	private:
		byte PD_SCK;	// Power Down and Serial Clock Input Pin
		byte DOUT;		// Serial Data Output Pin
		byte GAIN;		// amplification factor
		long OFFSET;	// used for tare weight
		float SCALE;	// used to return weight in grams, kg, ounces, whatever

	public:
		
		HX711(byte dout, byte pd_sck, byte gain = 128);

		virtual ~HX711();

		bool is_ready();

		void set_gain(byte gain = 128);

		long read();

		long read_average(byte times = 10);

                //added
                //long averageValue(byte times = 32);
                

		double get_value(byte times = 1);

		float get_units(byte times = 1);

		void tare(byte times = 10);

		void set_scale(float scale = 1.f);

		float get_scale();

                //added
                float get_Gram();

		void set_offset(long offset = 0);

		long get_offset();

		void power_down();

		void power_up();
};

#endif /* HX711_h */
