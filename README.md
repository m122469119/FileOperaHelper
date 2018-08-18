# FileOperaHelper

标签（空格分隔）：Android:夯实基础 

---

# 摘要
App文件存储位置和场景分析

# 版本差异

4.4以前,机身存储就是内部存储,外置SD卡就是外置存储<br>
通过getDataDirectory就可以获取内置存储根路径<br>
通过getExternalStorageDirectory就可以获取外置SD卡根路径<br>

4.4以后,机身存储包含了内部存储和外置存储,以及外置SD卡的外置存储<br>
通过getExternalStorageDirectory获取的是机身存储的外部存储<br>
而外置SD卡我们则需要通过getExternalDirs遍历来获取了<br> 

# 获取内部存储文件夹



# 获取外部存储文件夹



# 场景

- 保存大文本数据到本地

- 从本地读取大文本数据

- 拷贝Assets下的文件到手机存储

- 拷贝Raw下的文件到手机存储

- 保存网络下载的图片(将图片加载库的图片缓存文件拷贝到本地)

- 删除文本数据








 




