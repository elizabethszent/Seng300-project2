//Elizabeth Szentmiklossy UCID: 30165216
//Justine Mangaliman UCID: 30164741
//Enzo Mutiso UCID: 30182555
//Abdelrahman Mohamed UCID: 30162037
//Mohammad Mustafa Mehtab UCID: 30189394 
package com.thelocalmarketplace.software;

import com.jjjwelectronics.AbstractDevice;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.tdc.AbstractComponent;
import com.tdc.IComponent;

import java.util.ArrayList;

/**
 * Session class includes all
 * A Session object is created for each new user session
 * Constructed with arguments of a list of devices or components controlled by the software
 */

public class Session implements WeightDiscrepancyListner {
	private int status;  // 0 for not started, 1 for started and running, 2 for frozen (due to weight discrepency)
	private ArrayList<AbstractDevice> hardwareDevices = new ArrayList<AbstractDevice>();
	private ArrayList<AbstractComponent> hardwareComponents = new ArrayList<AbstractComponent>();

	public Session (Object... args) {
		for (Object arg : args) {
			if (arg instanceof AbstractDevice) {
				AbstractDevice temp_device = (AbstractDevice) arg;
				hardwareDevices.add(temp_device);
			} else if (arg instanceof AbstractComponent) {
				AbstractComponent temp_component = (AbstractComponent) arg;
				hardwareComponents.add(temp_component);
			} else {
				System.out.printf("Argument %s is neither a device or component.", arg);
			}
			this.freezeSession();
			status = 0;
		}
	}

	public void setHardwareComponents(ArrayList<AbstractComponent> hardwareComponents) {
		this.hardwareComponents = hardwareComponents;
	}

	public void setHardwareDevices(ArrayList<AbstractDevice> hardwareDevices) {
		this.hardwareDevices = hardwareDevices;
	}

	public void addHardwareComponent(AbstractComponent... components) {
		for (AbstractComponent component : components) {
			hardwareComponents.add(component);
		}
	}

	public void addHardwareDevice(AbstractDevice... devices) {
		for (AbstractDevice device : devices) {
			hardwareDevices.add(device);
		}
	}

	public int getStatus() {
		return this.status;
	}

	public void startSession() {
		if (status == 0) {
			status = 1;
			unfreezeSession();
		}
	}

	public void freezeSession() {
		status = 2;
		for (IDevice device : hardwareDevices) {
			device.disable();
		}
		for (IComponent component : hardwareComponents) {
			component.disable();
		}
	}

	public void unfreezeSession() {
		status = 1;
		for (IDevice device : hardwareDevices) {
			device.enable();
		}
		for (IComponent component : hardwareComponents) {
			component.enable();
		}
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {

	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {

	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {

	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {

	}

	@Override
	public void WeightDiscrancyOccurs() {
		if (status == 1) {
			this.freezeSession();
		}
	}

	@Override
	public void WeightDiscrancyResolved() {
		if (status == 2) {
			this.unfreezeSession();
		}
	}
}