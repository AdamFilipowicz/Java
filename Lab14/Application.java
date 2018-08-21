public class Application implements ApplicationMBean {

    private int threadNumber;
    private int memorySize;
    
    private int takenMemory;
    private long memoryTaken;
    private float errorMemoryPercentage;
    
    public Application(int numThreads, int schema){
        this.threadNumber=numThreads;
        this.memorySize=schema;
    }
    
    @Override
    public void setThreadNumber(int noOfThreads) {
        this.threadNumber=noOfThreads;
    }

    @Override
    public int getThreadNumber() {
        return this.threadNumber;
    }

    @Override
    public void setMemorySize(int memorySize) {
        this.memorySize=memorySize;
    }

    @Override
    public int getMemorySize() {
        return this.memorySize;
    }

    public void setTakenMemory(int takenMemory) {
        this.takenMemory = takenMemory;
    }

    public void setMemoryTaken(long memoryTaken) {
        this.memoryTaken = memoryTaken;
    }

    public void setErrorMemoryPercentage(float errorMemoryPercentage) {
        this.errorMemoryPercentage = errorMemoryPercentage;
    }
    
    @Override
    public String doPrintAppState(){
        return "Number of Threads: "+threadNumber+"\nAll memory size: "+memorySize+"\nTaken memory slots: "+takenMemory+"\nFree memory slots: "+(memorySize-takenMemory)+"\nMemory taken [b]: "+memoryTaken+"\nMemory errors percentage: "+errorMemoryPercentage;
    }

}