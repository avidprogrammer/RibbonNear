package accl;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class accelerometer implements SensorEventListener, MoveNotify {
	private Sensor accelerometer;
	private SensorManager sm;
	private float cur_val[] = new float[] { 0, 0, 0 };
	private MoveNotify mn;

	public accelerometer(MoveNotify evt) {
		mn = evt;
		sm = (SensorManager) ((Activity) mn).getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveNotify(float[] new_cord) {
		// TODO Auto-generated method stub
		mn.moveNotify(new_cord);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if ((has_moved_alot(cur_val[0], event.values[0]))
				|| (has_moved_alot(cur_val[1], event.values[1]))
				|| (has_moved_alot(cur_val[2], event.values[2])))
		{
			cur_val[0] = event.values[0];
			cur_val[1] = event.values[1];
			cur_val[2] = event.values[2];
			mn.moveNotify(cur_val);
		}

	}

	public boolean has_moved_alot(float orig, float curr) {
		return Math.abs(orig - curr) > 10;
	}

}