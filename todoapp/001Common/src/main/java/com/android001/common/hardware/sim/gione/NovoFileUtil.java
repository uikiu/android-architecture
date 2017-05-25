package com.android001.common.hardware.sim.gione;

import android.content.Context;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NovoFileUtil {
    private static final boolean DEBUG = false;
    private static final String TIMESTAMP_EXT = ".t";

    public static InputStream openLatestInputFile(Context context, String str) {
        InputStream inputStream = null;
        if ((readFileTimestamp(context, str) < readAssetsTimestamp(context, str) ? 1 : null) == null) {
            try {
                inputStream = context.openFileInput(str);
            } catch (Exception e) {
            }
        }
        if (inputStream == null) {
            try {
                inputStream = context.getAssets().open(str);
            } catch (Exception e2) {
            }
        }
        return inputStream;
    }

    public static InputStream openLatestInputFile(Context context, String str, String str2) {
        InputStream inputStream = null;
        if ((readFileTimestamp(context, str2) < readAssetsTimestamp(context, str, str2) ? 1 : null) == null) {
            try {
                inputStream = context.openFileInput(str2);
            } catch (Exception e) {
            }
        }
        if (inputStream == null) {
            try {
                inputStream = context.getAssets().open(str + str2);
            } catch (Exception e2) {
            }
        }
        return inputStream;
    }

    public static long readAssetsTimestamp(Context context, String str) {
        try {
            return readTimestampFromStream(context.getAssets().open(str + TIMESTAMP_EXT));
        } catch (Exception e) {
            return 0;
        }
    }

    public static long readAssetsTimestamp(Context context, String str, String str2) {
        try {
            return readTimestampFromStream(context.getAssets().open(str + str2 + TIMESTAMP_EXT));
        } catch (Exception e) {
            return 0;
        }
    }

    public static long readFileTimestamp(Context context, String str) {
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(str + TIMESTAMP_EXT);
        } catch (Exception e) {
        }
        return inputStream == null ? 0 : readTimestampFromStream(inputStream);
    }

    public static long readTimestampFromStream(InputStream inputStream) {
        DataInputStream dataInputStream;
        Throwable th;
        long j = 0;
        try {
            dataInputStream = new DataInputStream(inputStream);
            try {
                j = Long.parseLong(dataInputStream.readLine());
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (Exception e) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception e3) {
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (Exception e4) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e5) {
                    }
                }
                return j;
            }

            catch (Throwable th2) {
                th = th2;
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (Exception e6) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e7) {
                    }
                }
                throw th;
            }
        }
//        catch (Exception e8) {
//            dataInputStream = null;
//            if (dataInputStream != null) {
//                dataInputStream.close();
//            }
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            return j;
//        }
        catch (Throwable th3) {
            th = th3;
            dataInputStream = null;
            if (dataInputStream != null) {

                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
//            throw th;
        }
        return j;
    }

    public static void writeTimestamp2File(Context context, String str, long j) {
        FileOutputStream openFileOutput;
        FileOutputStream fileOutputStream;
        Throwable th;
        DataOutputStream dataOutputStream = null;
        try {
            openFileOutput = context.openFileOutput(str + TIMESTAMP_EXT, 0);
            try {
                DataOutputStream dataOutputStream2 = new DataOutputStream(openFileOutput);
                try {
                    dataOutputStream2.writeBytes(String.valueOf(j));
                    if (dataOutputStream2 != null) {
                        try {
                            dataOutputStream2.close();
                        } catch (Exception e) {
                        }
                    }
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (Exception e2) {
                        }
                    }
                } catch (IOException e3) {
                    fileOutputStream = openFileOutput;
                    dataOutputStream = dataOutputStream2;
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (Exception e4) {
                        }
                    }
                    if (fileOutputStream == null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e5) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = dataOutputStream2;
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (Exception e6) {
                        }
                    }
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (Exception e7) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e8) {
                fileOutputStream = openFileOutput;
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (fileOutputStream == null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th3) {
                th = th3;
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (openFileOutput != null) {
                    openFileOutput.close();
                }
                throw th;
            }
        } catch (IOException e9) {
            fileOutputStream = null;
            if (dataOutputStream != null) {

                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (fileOutputStream == null) {

                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Throwable th4) {
            th = th4;
            openFileOutput = null;
            if (dataOutputStream != null) {

                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (openFileOutput != null) {

                try {
                    openFileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
//            throw th;
        }
    }
}
