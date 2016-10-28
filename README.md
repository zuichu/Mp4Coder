# Mp4Coder
Mp4Coder，H264和AAC合成MP4视频库


###2016.10.26更新库：    
  
支持流媒体AAC音频合成
  
 
   
主要支持功能：  

*H264和AAC合成MP4视频文件  
-MP4文件拼接成MP4视频文件  
-AAC文件拼接成AAC音频文件  
-MP4文件裁剪、剪切  
-AAC文件裁剪、剪切  
-MP4文件提取分离AAC音频文件
-MP4文件提取分离H264 文件  
  
  
    


用法


```
   try {
       H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/imgBufFrame.h264"));
       AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/test.aac"));
       Movie movie = new Movie();
       movie.addTrack(h264Track);
       movie.addTrack(aacTrack);
       Container mp4file = new DefaultMp4Builder().build(movie);
       FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/encode.mp4")).getChannel();
       mp4file.writeContainer(fc);
       fc.close();
    } catch (IOException e) {
      e.printStackTrace();
   }

```

也可以根据实际情况自定义帧率：  
   
```
 H264TrackImpl h264Track1 = new H264TrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/video.h264"), "eng", 15000, 1001);
 
给出源码解释：

 /**
     * Creates a new <code>Track</code> object from a raw H264 source (<code>DataSource dataSource1</code>).
     * Whenever the timescale and frametick are set to negative value (e.g. -1) the H264TrackImpl
     * tries to detect the frame rate.
     * Typically values for <code>timescale</code> and <code>frametick</code> are:
     * <ul>
     * <li>23.976 FPS: timescale = 24000; frametick = 1001</li>
     * <li>25 FPS: timescale = 25; frametick = 1</li>
     * <li>29.97 FPS: timescale = 30000; frametick = 1001</li>
     * <li>30 FPS: timescale = 30; frametick = 1</li>
     * </ul>
     *
     * @param dataSource the source file of the H264 samples
     * @param lang       language of the movie (in doubt: use "eng")
     * @param timescale  number of time units (ticks) in one second
     * @param frametick  number of time units (ticks) that pass while showing exactly one frame
     * @throws IOException in case of problems whiel reading from the <code>DataSource</code>
     */
    public H264TrackImpl(DataSource dataSource, String lang, long timescale, int frametick) throws IOException {
        super(dataSource);
        this.lang = lang;
        this.timescale = timescale; //e.g. 23976
        this.frametick = frametick;
        if ((timescale > 0) && (frametick > 0)) {
            this.determineFrameRate = false;
        }

        parse(new LookAhead(dataSource));
    }
```
   
   
可以实现H264编码视频文件和AAC音频文件合成为MP4视频文件，也可以实现多个mp4文件拼接。  

```
//拼接示例  

 List<String> fileList = new ArrayList<String>();
        List<Movie> moviesList = new LinkedList<Movie>();
        fileList.add(Environment.getExternalStorageDirectory() + "/1.mp4");
        fileList.add(Environment.getExternalStorageDirectory() + "/2.mp4");
        try {
            for (String file : fileList) {
                moviesList.add(MovieCreator.build(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();
        for (Movie m : moviesList) {
            for (Track t : m.getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }

        Movie result = new Movie();

        try {
            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Container out = new DefaultMp4Builder().build(result);

        try {
            FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/encode.mp4")).getChannel();
            out.writeContainer(fc);
            fc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        moviesList.clear();
        fileList.clear();

```

   
MP4文件的信息解析  
  
```
  
  String localFilePath = Environment.getExternalStorageDirectory() + "/test.mp4";
        IsoFile isoFile = null;
        try {
            isoFile = new IsoFile(new RandomAccessFile(localFilePath, "r").getChannel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isoFile == null) {
            Log.i("info", "isoFile == null");
            return;
        }

        List<Box> boxes = isoFile.getBoxes();
        Log.i("info", "box size:" + boxes.size());
        MovieBox moov = isoFile.getBoxes(MovieBox.class).get(0);
        if (moov != null) {
            int nLen = moov.getBoxes().size();
            Log.i("info", "movie box:" + nLen);
            MovieHeaderBox hb = moov.getMovieHeaderBox();
            Log.i("info", "MovieHeaderBox:" + hb);
            Log.d("info", "duration:" + hb.getDuration());
        }
    }
      
      

```
  
  
MP4文件的剪切  

```
  
  public class ClipUtils {

    private static final String TAG = "info";

    /**
     * 截取指定时间段的视频
     *
     * @param inPath 视频的路径
     * @param begin  需要截取的开始时间
     * @param end    截取的结束时间
     * @throws IOException
     */
    public static void clipVideo(String inPath, String outPath, double begin, double end)
            throws IOException {
        Movie movie = MovieCreator.build(inPath);

        List<Track> tracks = movie.getTracks();
        movie.setTracks(new LinkedList<Track>());
        // remove all tracks we will create new tracks from the old

        double startTime1 = begin;
        double endTime1 = end;
        // double startTime2 = 30;
        // double endTime2 = 40;

        boolean timeCorrected = false;

        // Here we try to find a track that has sync samples. Since we can only
        // start decoding
        // at such a sample we SHOULD make sure that the start of the new
        // fragment is exactly
        // such a frame
        for (Track track : tracks) {
            if (track.getSyncSamples() != null
                    && track.getSyncSamples().length > 0) {
                if (timeCorrected) {
                    // This exception here could be a false positive in case we
                    // have multiple tracks
                    // with sync samples at exactly the same positions. E.g. a
                    // single movie containing
                    // multiple qualities of the same video (Microsoft Smooth
                    // Streaming file)
                    Log.e(TAG,
                            "The startTime has already been corrected by another track with SyncSample. Not Supported.");
                    throw new RuntimeException(
                            "The startTime has already been corrected by another track with SyncSample. Not Supported.");
                }
                startTime1 = correctTimeToSyncSample(track, startTime1, false);
                endTime1 = correctTimeToSyncSample(track, endTime1, true);
                // startTime2 = correctTimeToSyncSample(track, startTime2,
                // false);
                // endTime2 = correctTimeToSyncSample(track, endTime2, true);
                timeCorrected = true;
            }
        }

        for (Track track : tracks) {
            long currentSample = 0;
            double currentTime = 0;
            double lastTime = 0;
            long startSample1 = -1;
            long endSample1 = -1;
            // long startSample2 = -1;
            // long endSample2 = -1;

            for (int i = 0; i < track.getSampleDurations().length; i++) {
                long delta = track.getSampleDurations()[i];

                if (currentTime > lastTime && currentTime <= startTime1) {
                    // current sample is still before the new starttime
                    startSample1 = currentSample;
                }
                if (currentTime > lastTime && currentTime <= endTime1) {
                    // current sample is after the new start time and still
                    // before the new endtime
                    endSample1 = currentSample;
                }
                // if (currentTime > lastTime && currentTime <= startTime2) {
                // // current sample is still before the new starttime
                // startSample2 = currentSample;
                // }
                // if (currentTime > lastTime && currentTime <= endTime2) {
                // // current sample is after the new start time and still
                // before the new endtime
                // endSample2 = currentSample;
                // }
                lastTime = currentTime;
                currentTime += (double) delta
                        / (double) track.getTrackMetaData().getTimescale();
                currentSample++;
            }
            movie.addTrack(new CroppedTrack(track, startSample1, endSample1));// new
            // AppendTrack(new
            // CroppedTrack(track,
            // startSample1,
            // endSample1),
            // new
            // CroppedTrack(track,
            // startSample2,
            // endSample2)));
        }
        long start1 = System.currentTimeMillis();
        Container out = new DefaultMp4Builder().build(movie);
        long start2 = System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(outPath);
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);

        fc.close();
        fos.close();
        long start3 = System.currentTimeMillis();
        Log.e(TAG, "Building IsoFile took : " + (start2 - start1) + "ms");
        Log.e(TAG, "Writing IsoFile took : " + (start3 - start2) + "ms");
        Log.e(TAG,
                "Writing IsoFile speed : "
                        + (new File(String.format("output-%f-%f.mp4",
                        startTime1, endTime1)).length()
                        / (start3 - start2) / 1000) + "MB/s");
    }

    private static double correctTimeToSyncSample(Track track, double cutHere,
                                                  boolean next) {
        double[] timeOfSyncSamples = new double[track.getSyncSamples().length];
        long currentSample = 0;
        double currentTime = 0;
        for (int i = 0; i < track.getSampleDurations().length; i++) {
            long delta = track.getSampleDurations()[i];

            if (Arrays.binarySearch(track.getSyncSamples(), currentSample + 1) >= 0) {
                // samples always start with 1 but we start with zero therefore
                // +1
                timeOfSyncSamples[Arrays.binarySearch(track.getSyncSamples(),
                        currentSample + 1)] = currentTime;
            }
            currentTime += (double) delta
                    / (double) track.getTrackMetaData().getTimescale();
            currentSample++;

        }
        double previous = 0;
        for (double timeOfSyncSample : timeOfSyncSamples) {
            if (timeOfSyncSample > cutHere) {
                if (next) {
                    return timeOfSyncSample;
                } else {
                    return previous;
                }
            }
            previous = timeOfSyncSample;
        }
        return timeOfSyncSamples[timeOfSyncSamples.length - 1];
    }

}  
  
  

```
  
    
MP4文件提取AAC音频文件
  
```
  
  List<Track> audioTracks = new LinkedList<Track>();
        try {
            Movie movie = MovieCreator.build(Environment.getExternalStorageDirectory() + "/input.mp4");
            for (Track t : movie.getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
            }
            Movie result = new Movie();
            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            Container out = new DefaultMp4Builder().build(result);
            FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/output.aac")).getChannel();
            out.writeContainer(fc);
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
```  
  
  当然也可以实现MP4提取视频文件，H264和网络AAC文件合并MP4操作等其他操作...
    
  其他用法和功能请看examples里的文件用法实例
    
    

可使用aar库，方便些。  
此为Android库  
