/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    
    private int[] dayCounts;
    private int[] monthCounts;
    private int[][] fiveYear;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[29];
        monthCounts = new int[13];
        fiveYear = new int[5][13];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    public LogAnalyzer(String getFile)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[29];
        monthCounts = new int[13];
        fiveYear = new int[5][13];
        // Create the reader to obtain the data.
        reader = new LogfileReader(getFile);
    }
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
        reader.reset();
    }

    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
        reader.reset();
    }
    
    public void analyzeFiveYear()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int year = entry.getYear() - 2014;
            int month = entry.getMonth();
            fiveYear[year][month]++;
        }
        reader.reset();   
    }
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
        }
        reader.reset();
    }
    public int busiestHour()
    {
        int busy = 0;
        int index = 0;
        
        for (int n = 0; n < hourCounts.length; n++)
        {
            if (hourCounts[n] > busy)
            {
                busy = hourCounts[n];
                index = n;
            }
        }
        return index;
    }
    
    public int quietestHour()
    {
        int busy = hourCounts[0];
        int index = 0;
        
        for (int n = 0; n < hourCounts.length; n++)
        {
            if (hourCounts[n] < busy)
            {
                busy = hourCounts[n];
                index = n;
            }
        }
        return index;
    }
    
    public int busiestTwoHour()
    {
       int busy = 0;
       int index = 0;
        
        for (int n = 0; n < hourCounts.length - 1; n++)
        {
            if (hourCounts[n] + hourCounts[n+1] > busy)
            {
                busy = hourCounts[n] + hourCounts[n+1];
                index = n;
            }
        }
        return index;
    }
    
    public int busiestDay()
    {
       int busy = 0;
       int index = 0;
        
        for (int n = 0; n < dayCounts.length; n++)
        {
            if (dayCounts[n] > busy)
            {
                busy = dayCounts[n];
                index = n;
            }
        }
        return index; 
    }
    public int quietestDay()
    {
       int busy = dayCounts[1];
       int index = 0;
        
        for (int n = 1; n < dayCounts.length; n++)
        {
            if (dayCounts[n] < busy)
            {
                busy = dayCounts[n];
                index = n;
            }
        }
        return index; 
    }
    
    public void totalAccessesPerMonth()
    {
        System.out.println("Month: Accesses");
        for(int month = 1; month < monthCounts.length; month++) {
            System.out.println(month + ": " + monthCounts[month]);
        }
    }
    
    public void quietestMonth()
    {
       int busy = fiveYear[0][1];
       int index = 0;
       int month = 1;
        
        for (int n = 0; n < fiveYear.length; n++)
        {
            for (int i = 1; i < fiveYear[n].length; i++)
            {
                if (fiveYear[n][i] < busy)
                {
                    busy = fiveYear[n][i];
                    index = n;
                    month = i;
                }
            }
        }
        System.out.println("The quietest month was Year " 
                            + (index + 2014) + " Month " + month);
        
    }
    
    public void busiestMonth()
    {
       int busy = 0;
       int index = 0;
       int month = 1;
        
        for (int n = 0; n < fiveYear.length; n++)
        {
            for (int i = 1; i < fiveYear[n].length; i++)
            {
                if (fiveYear[n][i] > busy)
                {
                    busy = fiveYear[n][i];
                    index = n;
                    month = i;
                }
            }
        }
        System.out.println("The busiest month was Year " 
                            + (index + 2014) + " Month " + month);
        
    }
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * 
     */
    public int numberOfAccessors()
    {
        int total = 0; 
        
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            total += hourCounts[hour];
        }
        
        return total;
    }
    
    public void averageAccessesPerMonth()
    {
        System.out.println("Month: Average Accesses");
        for(int month = 1; month < monthCounts.length; month++) {
            System.out.println(month + ": " + (double)monthCounts[month]/5);
        }
    }
}
