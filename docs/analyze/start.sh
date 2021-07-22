#!/bin/bash

HOME=/Users/localuser/Projects/hsa/template/hsa-template-java
TAG=12091503

cd $HOME
mvn clean install

######## ali
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate ali dependency tree with verbose ..."
cd $HOME/hsa-main-ali
mvn dependency:tree -Dverbose >$TAG-tree-verbose.txt
mv $TAG-tree-verbose.txt $HOME/docs/analyze/ali
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate ali dependency tree ..."
mvn dependency:tree >$TAG-tree.txt
mv $TAG-tree.txt $HOME/docs/analyze/ali
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate ali dependency analyze ..."
mvn dependency:analyze >$TAG-analyze.txt
mv $TAG-analyze.txt $HOME/docs/analyze/ali
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate ali dependency list ..."
mvn dependency:list >$TAG-list.txt
mv $TAG-list.txt $HOME/docs/analyze/ali
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate ali dependency lib list ..."
cd $HOME/hsa-main-ali/target
mkdir hsa-xxx-ali-1.0.0-SNAPSHOT
unzip -q hsa-xxx-ali-1.0.0-SNAPSHOT.jar -d hsa-xxx-ali-1.0.0-SNAPSHOT
cd hsa-xxx-ali-1.0.0-SNAPSHOT/BOOT-INF/lib
ls -lR |grep -v ^d|awk '{print $9}'| sort -f >$TAG-lib.txt
mv $TAG-lib.txt $HOME/docs/analyze/ali

######## tencent
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate tencent dependency tree with verbose ..."
cd $HOME/hsa-main-tencent
mvn dependency:tree -Dverbose >$TAG-tree-verbose.txt
mv $TAG-tree-verbose.txt $HOME/docs/analyze/tencent
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate tencent dependency tree ..."
mvn dependency:tree >$TAG-tree.txt
mv $TAG-tree.txt $HOME/docs/analyze/tencent
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate tencent dependency analyze ..."
mvn dependency:analyze >$TAG-analyze.txt
mv $TAG-analyze.txt $HOME/docs/analyze/tencent
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate tencent dependency list ..."
mvn dependency:list >$TAG-list.txt
mv $TAG-list.txt $HOME/docs/analyze/tencent
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate tencent dependency lib list ..."
cd $HOME/hsa-main-tencent/target
mkdir hsa-xxx-tencent-1.0.0-SNAPSHOT
unzip -q hsa-xxx-tencent-1.0.0-SNAPSHOT.jar -d hsa-xxx-tencent-1.0.0-SNAPSHOT
cd hsa-xxx-tencent-1.0.0-SNAPSHOT/BOOT-INF/lib
ls -lR |grep -v ^d|awk '{print $9}'| sort -f >$TAG-lib.txt
mv $TAG-lib.txt $HOME/docs/analyze/tencent

######## generic
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate generic dependency tree with verbose ..."
cd $HOME/hsa-main-generic
mvn dependency:tree -Dverbose >$TAG-tree-verbose.txt
mv $TAG-tree-verbose.txt $HOME/docs/analyze/generic
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate generic dependency tree ..."
mvn dependency:tree >$TAG-tree.txt
mv $TAG-tree.txt $HOME/docs/analyze/generic
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate generic dependency analyze ..."
mvn dependency:analyze >$TAG-analyze.txt
mv $TAG-analyze.txt $HOME/docs/analyze/generic
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate generic dependency list ..."
mvn dependency:list >$TAG-list.txt
mv $TAG-list.txt $HOME/docs/analyze/generic
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate generic dependency lib list ..."
cd $HOME/hsa-main-generic/target
mkdir hsa-xxx-generic-1.0.0-SNAPSHOT
unzip -q hsa-xxx-generic-1.0.0-SNAPSHOT.jar -d hsa-xxx-generic-1.0.0-SNAPSHOT
cd hsa-xxx-generic-1.0.0-SNAPSHOT/BOOT-INF/lib
ls -lR |grep -v ^d|awk '{print $9}'| sort -f >$TAG-lib.txt
mv $TAG-lib.txt $HOME/docs/analyze/generic

echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Generate all finished."

