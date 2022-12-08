package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        ArrayList<String> lineList = new ArrayList<String>();
        Path path = Paths.get("src",  "day7/input.txt");

        try {
            lineList = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<VirtualDirectory> virtualDirectoryList = new ArrayList<VirtualDirectory>();

        VirtualDirectory rootDir = new VirtualDirectory("/", "/", null);

        VirtualDirectory currentDir = rootDir;


        for (ListIterator<String> listIterator = lineList.listIterator(); listIterator.hasNext(); ) {
            String line = (String) listIterator.next();
            if(line.contains("$ cd")) {
                String moveToDirectoryName = line.split("\\$ cd ")[1];
                if(moveToDirectoryName.equals("..")) {
                    currentDir = currentDir.parent;
                } else if (moveToDirectoryName.equals("/")) {
                    currentDir = rootDir;
                } else {
                    VirtualDirectory finalCurrentDir = currentDir;
                    if(!currentDir.directories.stream().findAny().filter(d -> d.path.equals(finalCurrentDir.path + moveToDirectoryName + "/")).isPresent()) {
                        VirtualDirectory newDir = new VirtualDirectory(moveToDirectoryName, currentDir.path + moveToDirectoryName + "/", currentDir);
                        currentDir.directories.add(newDir);
                        virtualDirectoryList.add(newDir);
                        currentDir = newDir;
                    } else {
                        VirtualDirectory finalCurrentDir1 = currentDir;
                        currentDir = currentDir.directories.stream().filter(d -> d.path.equals(finalCurrentDir1.path + moveToDirectoryName + "/")).findFirst().get();
                    }
                }
            }
            if(line.startsWith("$ ls")) {
                line = listIterator.next();
                while(!line.startsWith("$")) {
                    if(line.startsWith("dir")) {
                        String newDirName = line.split("dir ")[1];
                        VirtualDirectory finalCurrentDir2 = currentDir;
                        if(!currentDir.directories.stream().findAny().filter(d -> d.path.equals(finalCurrentDir2.path + newDirName + "/")).isPresent()) {
                            VirtualDirectory newDir = new VirtualDirectory(newDirName, currentDir.path + newDirName + "/", currentDir);
                            currentDir.directories.add(newDir);
                            virtualDirectoryList.add(newDir);
                        }
                    }
                    else {
                        long fileSize = Long.parseLong(line.split(" ")[0]);
                        //System.out.println("File size: " + fileSize);
                        String fileName = line.split(" ")[1];
                        currentDir.files.add(new VirtualFile(fileName, fileSize));
                        currentDir.totalSize += fileSize;
                        updateParentDirSizes(currentDir, fileSize);
                    }
                    if(listIterator.hasNext()) {
                        line = listIterator.next();
                    } else {
                        break;
                    }
                }
                listIterator.previous();
            }

        }

        for (VirtualDirectory virtualDirectory : virtualDirectoryList) {
            System.out.println(virtualDirectory.path + " " + virtualDirectory.totalSize);
            for (VirtualFile virtualFile : virtualDirectory.files) {
                System.out.println("---> " + virtualFile.name + " " + virtualFile.size);
            }
        }

        // get all VirtualDirectories with totalSize < 100000 with the use of lambda expression
        ArrayList<VirtualDirectory> smallDirectories = virtualDirectoryList.stream().filter(d -> d.totalSize < 100000).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);


        int sum = 0;
        for (VirtualDirectory smallDirectory : smallDirectories) {
            System.out.println(smallDirectory.path + " " + smallDirectory.totalSize);
            for (VirtualFile virtualFile : smallDirectory.files) {
                System.out.println("---> " + virtualFile.name + " " + virtualFile.size);
            }
            sum += smallDirectory.totalSize;
        }


        // int sumOfSmallDirectoriesSizes = smallDirectories.stream().mapToInt(d -> (int) d.totalSize).sum();

        System.out.println("Sum of small directories sizes: " + sum);

        long neededSpace = (30000000 - (70000000 - rootDir.totalSize));
        System.out.println("root directory size: " + rootDir.totalSize);
        System.out.println("available space: " + (70000000 - rootDir.totalSize));
        System.out.println("needed space for update: " + neededSpace);



        Collections.sort(virtualDirectoryList, (d1, d2) -> (int) (d2.totalSize - d1.totalSize));

        /*
        // print out all virtual directories
        for (VirtualDirectory virtualDirectory : virtualDirectoryList) {
            System.out.println(virtualDirectory.path + " " + virtualDirectory.totalSize);
        }

         */
        List<VirtualDirectory> virtualDirectoriesCandidates = virtualDirectoryList.stream().filter(d -> d.totalSize > neededSpace).toList();
        VirtualDirectory bestCandidate = virtualDirectoriesCandidates.get(virtualDirectoriesCandidates.size()-1);
                ;
        System.out.println("best candidate: " + bestCandidate.path + " " + bestCandidate.totalSize);

    }

    public static void updateParentDirSizes(VirtualDirectory dir, long newFileSize) {
        if(dir.parent != null) {
            dir.parent.totalSize += newFileSize;
            updateParentDirSizes(dir.parent, newFileSize);
        }
    }

    private static class VirtualFile {
        String name;
        long size;

        public VirtualFile(String name, long size) {
            this.name = name;
            this.size = size;
        }
    }
    private static class VirtualDirectory {
        String name;
        ArrayList<VirtualFile> files;
        ArrayList<VirtualDirectory> directories;
        long totalSize;
        final String path;

        VirtualDirectory parent;

        public VirtualDirectory(String name, String path, VirtualDirectory parent) {
            this.name = name;
            this.files = new ArrayList<VirtualFile>();
            this.directories = new ArrayList<VirtualDirectory>();
            this.path = path;
            this.parent = parent;
        }
    }

}
