// class to get a set of evenly distributed points
public class Linspace{
	public double [] getValues(){
		double t[] = {0.0000, 0.0840, 0.1680, 0.2520, 0.3360, 0.4200, 0.5040, 0.5880, 0.6720, 0.7560,
				      0.8400, 0.9240, 1.0080, 1.0920, 1.1760, 1.2600, 1.3440, 1.4280, 1.5120, 1.5960,
				      1.6800, 1.7640, 1.8480, 1.9320, 2.0160, 2.1000, 2.1840, 2.2680, 2.3520, 2.4360,
				      2.5200, 2.6040, 2.6880, 2.7720, 2.8560, 2.9400, 3.0240, 3.1080, 3.1920, 3.2760,
				      3.3600, 3.4440, 3.5280, 3.6120, 3.6960, 3.7800, 3.8640, 3.9480, 4.0320, 4.1160,
				      4.2000, 4.2840, 4.3680, 4.4520, 4.5360, 4.6200, 4.7040, 4.7880, 4.8720, 4.9560,
				      5.0400, 5.1240, 5.2080, 5.2920, 5.3760, 5.4600, 5.5440, 5.6280, 5.7120, 5.7960,
				      5.8800, 5.9640, 6.0480, 6.1320, 6.2160, 6.3000, 6.3840, 6.4680, 6.5520, 6.6360,
				      6.7200, 6.8040, 6.8880, 6.9720, 7.0560, 7.1400, 7.2240, 7.3080, 7.3920, 7.4760,
				      7.5600, 7.6440, 7.7280, 7.8120, 7.8960, 7.9800, 8.0640, 8.1480, 8.2320, 8.3160,
				      8.4000, 8.4840, 8.5680, 8.6520, 8.7360, 8.8200, 8.9040, 8.9880, 9.0720, 9.1560, 
				      9.2400, 9.3240, 9.4080, 9.4920, 9.5760, 9.6600, 9.7440, 9.8280, 9.9120, 9.9960, 
				      10.0800, 10.1640, 10.2480, 10.3320, 10.4160, 10.5000, 10.5840, 10.6680, 10.7520, 10.8360, 
				      10.9200, 11.0040, 11.0880, 11.1720, 11.2560, 11.3400, 11.4240, 11.5080, 11.5920, 11.6760, 
				      11.7600, 11.8440, 11.9280, 12.0120, 12.0960, 12.1800, 12.2640, 12.3480, 12.4320, 12.5160, 
				      12.6000, 12.6840, 12.7680, 12.8520, 12.9360, 13.0200, 13.1040, 13.1880, 13.2720, 13.3560, 
				      13.4400, 13.5240, 13.6080, 13.6920, 13.7760, 13.8600, 13.9440, 14.0280, 14.1120, 14.1960, 
				      14.2800, 14.3640, 14.4480, 14.5320, 14.6160, 14.7000, 14.7840, 14.8680, 14.9520, 15.0360, 
				      15.1200, 15.2040, 15.2880, 15.3720, 15.4560, 15.5400, 15.6240, 15.7080, 15.7920, 15.8760, 
				      15.9600, 16.0440, 16.1280, 16.2120, 16.2960, 16.3800, 16.4640, 16.5480, 16.6320, 16.7160, 
				      16.8000, 16.8840, 16.9680, 17.0520, 17.1360, 17.2200, 17.3040, 17.3880, 17.4720, 17.5560, 
				      17.6400, 17.7240, 17.8080, 17.8920, 17.9760, 18.0600, 18.1440, 18.2280, 18.3120, 18.3960, 
				      18.4800, 18.5640, 18.6480, 18.7320, 18.8160, 18.9000, 18.9840, 19.0680, 19.1520, 19.2360, 
				      19.3200, 19.4040, 19.4880, 19.5720, 19.6560, 19.7400, 19.8240, 19.9080, 19.9920, 20.0760, 
				      20.1600, 20.2440, 20.3280, 20.4120, 20.4960, 20.5800, 20.6640, 20.7480, 20.8320, 20.9160, 
				      21.0000, 21.0840, 21.1680, 21.2520, 21.3360, 21.4200, 21.5040, 21.5880, 21.6720, 21.7560, 
				      21.8400, 21.9240, 22.0080, 22.0920, 22.1760, 22.2600, 22.3440, 22.4280, 22.5120, 22.5960, 
				      22.6800, 22.7640, 22.8480, 22.9320, 23.0160, 23.1000, 23.1840, 23.2680, 23.3520, 23.4360, 
				      23.5200, 23.6040, 23.6880, 23.7720, 23.8560, 23.9400, 24.0240, 24.1080, 24.1920, 24.2760, 
				      24.3600, 24.4440, 24.5280, 24.6120, 24.6960, 24.7800, 24.8640, 24.9480, 25.0320, 25.1160, 
				      25.2000, 25.2840, 25.3680, 25.4520, 25.5360, 25.6200, 25.7040, 25.7880, 25.8720, 25.9560, 
				      26.0400, 26.1240, 26.2080, 26.2920, 26.3760, 26.4600, 26.5440, 26.6280, 26.7120, 26.7960, 
				      26.8800, 26.9640, 27.0480, 27.1320, 27.2160, 27.3000, 27.3840, 27.4680, 27.5520, 27.6360, 
				      27.7200, 27.8040, 27.8880, 27.9720, 28.0560, 28.1400, 28.2240, 28.3080, 28.3920, 28.4760, 
				      28.5600, 28.6440, 28.7280, 28.8120, 28.8960, 28.9800, 29.0640, 29.1480, 29.2320, 29.3160, 
				      29.4000, 29.4840, 29.5680, 29.6520, 29.7360, 29.8200, 29.9040, 29.9880, 30.0720, 30.1560, 
				      30.2400, 30.3240, 30.4080, 30.4920, 30.5760, 30.6600, 30.7440, 30.8280, 30.9120, 30.9960, 
				      31.0800, 31.1640, 31.2480, 31.3320, 31.4160, 31.5000, 31.5840, 31.6680, 31.7520, 31.8360, 
				      31.9200, 32.0040, 32.0880, 32.1720, 32.2560, 32.3400, 32.4240, 32.5080, 32.5920, 32.6760, 
				      32.7600, 32.8440, 32.9280, 33.0120, 33.0960, 33.1800, 33.2640, 33.3480, 33.4320, 33.5160, 
				      33.6000, 33.6840, 33.7680, 33.8520, 33.9360, 34.0200, 34.1040, 34.1880, 34.2720, 34.3560, 
				      34.4400, 34.5240, 34.6080, 34.6920, 34.7760, 34.8600, 34.9440, 35.0280, 35.1120, 35.1960, 
				      35.2800, 35.3640, 35.4480, 35.5320, 35.6160, 35.7000, 35.7840, 35.8680, 35.9520, 36.0360, 
				      36.1200, 36.2040, 36.2880, 36.3720, 36.4560, 36.5400, 36.6240, 36.7080, 36.7920, 36.8760, 
				      36.9600, 37.0440, 37.1280, 37.2120, 37.2960, 37.3800, 37.4640, 37.5480, 37.6320, 37.7160, 
				      37.8000, 37.8840, 37.9680, 38.0520, 38.1360, 38.2200, 38.3040, 38.3880, 38.4720, 38.5560, 
				      38.6400, 38.7240, 38.8080, 38.8920, 38.9760, 39.0600, 39.1440, 39.2280, 39.3120, 39.3960, 
				      39.4800, 39.5640, 39.6480, 39.7320, 39.8160, 39.9000, 39.9840, 40.0680, 40.1520, 40.2360, 
				      40.3200, 40.4040, 40.4880, 40.5720, 40.6560, 40.7400, 40.8240, 40.9080, 40.9920, 41.0760, 
				      41.1600, 41.2440, 41.3280, 41.4120, 41.4960, 41.5800, 41.6640, 41.7480, 41.8320, 41.9160, 
				      42.0000, 42.0840, 42.1680, 42.2520, 42.3360, 42.4200, 42.5040, 42.5880, 42.6720, 42.7560, 
				      42.8400, 42.9240, 43.0080, 43.0920, 43.1760, 43.2600, 43.3440, 43.4280, 43.5120, 43.5960, 
				      43.6800, 43.7640, 43.8480, 43.9320, 44.0160, 44.1000, 44.1840, 44.2680, 44.3520, 44.4360, 
				      44.5200, 44.6040, 44.6880, 44.7720, 44.8560, 44.9400, 45.0240, 45.1080, 45.1920, 45.2760, 
				      45.3600, 45.4440, 45.5280, 45.6120, 45.6960, 45.7800, 45.8640, 45.9480, 46.0320, 46.1160, 
				      46.2000, 46.2840, 46.3680, 46.4520, 46.5360, 46.6200, 46.7040, 46.7880, 46.8720, 46.9560, 
				      47.0400, 47.1240, 47.2080, 47.2920, 47.3760, 47.4600, 47.5440, 47.6280, 47.7120, 47.7960, 
				      47.8800, 47.9640, 48.0480, 48.1320, 48.2160, 48.3000, 48.3840, 48.4680, 48.5520, 48.6360, 
				      48.7200, 48.8040, 48.8880, 48.9720, 49.0560, 49.1400, 49.2240, 49.3080, 49.3920, 49.4760, 
				      49.5600, 49.6440, 49.7280, 49.8120, 49.8960, 49.9800, 50.0640, 50.1480, 50.2320, 50.3160, 
				      50.4000, 50.4840, 50.5680, 50.6520, 50.7360, 50.8200, 50.9040, 50.9880, 51.0720, 51.1560, 
				      51.2400, 51.3240, 51.4080, 51.4920, 51.5760, 51.6600, 51.7440, 51.8280, 51.9120, 51.9960, 
				      52.0800, 52.1640, 52.2480, 52.3320, 52.4160, 52.5000, 52.5840, 52.6680, 52.7520, 52.8360, 
				      52.9200, 53.0040, 53.0880, 53.1720, 53.2560, 53.3400, 53.4240, 53.5080, 53.5920, 53.6760, 
				      53.7600, 53.8440, 53.9280, 54.0120, 54.0960, 54.1800, 54.2640, 54.3480, 54.4320, 54.5160, 
				      54.6000, 54.6840, 54.7680, 54.8520, 54.9360, 55.0200, 55.1040, 55.1880, 55.2720, 55.3560, 
				      55.4400, 55.5240, 55.6080, 55.6920, 55.7760, 55.8600, 55.9440, 56.0280, 56.1120, 56.1960, 
				      56.2800, 56.3640, 56.4480, 56.5320, 56.6160, 56.7000, 56.7840, 56.8680, 56.9520, 57.0360, 
				      57.1200, 57.2040, 57.2880, 57.3720, 57.4560, 57.5400, 57.6240, 57.7080, 57.7920, 57.8760, 
				      57.9600, 58.0440, 58.1280, 58.2120, 58.2960, 58.3800, 58.4640, 58.5480, 58.6320, 58.7160, 
				      58.8000, 58.8840, 58.9680, 59.0520, 59.1360, 59.2200, 59.3040, 59.3880, 59.4720, 59.5560, 
				      59.6400, 59.7240, 59.8080, 59.8920, 59.9760, 60.0600, 60.1440, 60.2280, 60.3120, 60.3960, 
				      60.4800, 60.5640, 60.6480, 60.7320, 60.8160, 60.9000, 60.9840, 61.0680, 61.1520, 61.2360, 
				      61.3200, 61.4040, 61.4880, 61.5720, 61.6560, 61.7400, 61.8240, 61.9080, 61.9920, 62.0760, 
				      62.1600, 62.2440, 62.3280, 62.4120, 62.4960, 62.5800, 62.6640, 62.7480, 62.8320, 62.9160, 
				      63.0000, 63.0840, 63.1680, 63.2520, 63.3360, 63.4200, 63.5040, 63.5880, 63.6720, 63.7560, 
				      63.8400, 63.9240, 64.0080, 64.0920, 64.1760, 64.2600, 64.3440, 64.4280, 64.5120, 64.5960, 
				      64.6800, 64.7640, 64.8480, 64.9320, 65.0160, 65.1000, 65.1840, 65.2680, 65.3520, 65.4360, 
				      65.5200, 65.6040, 65.6880, 65.7720, 65.8560, 65.9400, 66.0240, 66.1080, 66.1920, 66.2760, 
				      66.3600, 66.4440, 66.5280, 66.6120, 66.6960, 66.7800, 66.8640, 66.9480, 67.0320, 67.1160, 
				      67.2000, 67.2840, 67.3680, 67.4520, 67.5360, 67.6200, 67.7040, 67.7880, 67.8720, 67.9560, 
				      68.0400, 68.1240, 68.2080, 68.2920, 68.3760, 68.4600, 68.5440, 68.6280, 68.7120, 68.7960, 
				      68.8800, 68.9640, 69.0480, 69.1320, 69.2160, 69.3000, 69.3840, 69.4680, 69.5520, 69.6360, 
				      69.7200, 69.8040, 69.8880, 69.9720, 70.0560, 70.1400, 70.2240, 70.3080, 70.3920, 70.4760, 
				      70.5600, 70.6440, 70.7280, 70.8120, 70.8960, 70.9800, 71.0640, 71.1480, 71.2320, 71.3160, 
				      71.4000, 71.4840, 71.5680, 71.6520, 71.7360, 71.8200, 71.9040, 71.9880, 72.0720, 72.1560, 
				      72.2400, 72.3240, 72.4080, 72.4920, 72.5760, 72.6600, 72.7440, 72.8280, 72.9120, 72.9960, 
				      73.0800, 73.1640, 73.2480, 73.3320, 73.4160, 73.5000, 73.5840, 73.6680, 73.7520, 73.8360, 
				      73.9200, 74.0040, 74.0880, 74.1720, 74.2560, 74.3400, 74.4240, 74.5080, 74.5920, 74.6760, 
				      74.7600, 74.8440, 74.9280, 75.0120, 75.0960, 75.1800, 75.2640, 75.3480, 75.4320, 75.5160, 
				      75.6000, 75.6840, 75.7680, 75.8520, 75.9360, 76.0200, 76.1040, 76.1880, 76.2720, 76.3560, 
				      76.4400, 76.5240, 76.6080, 76.6920, 76.7760, 76.8600, 76.9440, 77.0280, 77.1120, 77.1960, 
				      77.2800, 77.3640, 77.4480, 77.5320, 77.6160, 77.7000, 77.7840, 77.8680, 77.9520, 78.0360, 
				      78.1200, 78.2040, 78.2880, 78.3720, 78.4560, 78.5400, 78.6240, 78.7080, 78.7920, 78.8760, 
				      78.9600, 79.0440, 79.1280, 79.2120, 79.2960, 79.3800, 79.4640, 79.5480, 79.6320, 79.7160, 
				      79.8000, 79.8840, 79.9680, 80.0520, 80.1360, 80.2200, 80.3040, 80.3880, 80.4720, 80.5560, 
				      80.6400, 80.7240, 80.8080, 80.8920, 80.9760, 81.0600, 81.1440, 81.2280, 81.3120, 81.3960, 
				      81.4800, 81.5640, 81.6480, 81.7320, 81.8160, 81.9000, 81.9840, 82.0680, 82.1520, 82.2360, 
				      82.3200, 82.4040, 82.4880, 82.5720, 82.6560, 82.7400, 82.8240, 82.9080, 82.9920, 83.0760, 
				      83.1600, 83.2440, 83.3280, 83.4120, 83.4960, 83.5800, 83.6640, 83.7480, 83.8320, 83.9160,
				      84.0000};
		return t;
	}
}//end Linspace