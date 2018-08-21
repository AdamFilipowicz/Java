public interface ApplicationMBean {

    public void setThreadNumber(int threadNumber);
    public int getThreadNumber();
    
    public void setMemorySize(int memorySize);
    public int getMemorySize();
    
    public String doPrintAppState();
}