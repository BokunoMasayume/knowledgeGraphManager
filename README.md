# 四个部分：

1. neo4j的restful后台
   1. 实现节点和关系的增删查改
   2. 实现整个文件的查询
   3. 用户身份验证token
2. mongodb的restful后台
   1. 实现用户的注册，登录，验证token 
   2. 实现用户自定义的模板的存储，增删查改
   3. 用户自定义的主题信息
3. 前端使用vue
   1. 功能的完全实现
4. 图的可视化使用d3的力导向图



# 图存储大体逻辑

区分不同用户和不同文件中的节点使用标签，关系的从属自然通过节点的从属决定，用户一个标签，用户加文件一个标签



nodes:

- id 
- finalLabel: 标识显示图标的label
- labels: 所有label（除了POJO类的label）
- properties:节点的属性们



relations:

- id
- typeName:关系类型
- startPID: 开始节点id
- endPID:  结束节点id
- properties: 节点的属性们

apis:

- get   /graph/node?token=token&fileId=fileId

  - 该文件下所有节点

- delete /graph/node?token=token&fileId=fileId

  -  删除该文件下所有节点（逻辑删除。。。嗯，设置个property isDel=true）

- post /graph/node?token=token&fileId=fileId

  - 在该文件下添加节点 list

- delete /graph/node/id?token=token&fileId=fileId

  - 删除节点

- patch  /graph/node/id?token=token&fileId=fileId

  - 更新节点

- put /graph/node/id?token=token&fileId=fileId

  - 更新节点为

  

- get   /graph/relation?token=token&fileId=fileId

  - 该文件下所有关系

- delete /graph/relation?token=token&fileId=fileId

  - 删除该文件下所有关系（逻辑删除）

- post /graph/relation?token=token&fileId=fileId

  - 添加关系 list

- delete /graph/relation/id?token=token&fileId=fileId

  - 删除关系

- patch  /graph/relation/id?token=token&fileId=fileId

  - 更新关系

- put /graph/relation/id?token=token&fileId=fileId

  - 更新关系为



# 用户信息 mongo

- useId 
- username( unique index): 用户可以修改昵称，但昵称必须不合他人相同
- email **unique**
- phone number **unique**
- lastpwdresetdate
- register date stamp
- password
- avatar uri
- roles



apis:

- 注册 user
- 登录(gain token) user admin
- refresh token user admin
- -
- 添加角色 admin
- 删除角色 admin



**patch is a little different **



# 用户文件树信息 mongo

- userId **建个索引**
- isDelete
- filename
- isFolder
- parentid   **filename 和 parentid 上建立唯一索引**
- **fileid** **_id**

apis

- 添加文件
- 删除文件（先不支持嵌套删除）
- 修改文件名称
- 查询一个用户的全部文件

`db.testFile.ensureIndex({"parentId":1,"fileName":1},{unique:true})`

`db.testFile.ensureIndex({"userId":1})`



# 用户模板信息 mongo

- moduleId

- userId

- labelName **userId 和  labelName 唯一索引**

- describe

- isNode

- isAbstract

- parents:[id1,id2..]

- avataruri

- properties:{

  - key: {

    - default value:
    - constraint		

    }

  ​	}

`db.testList.find({lis:"ss"})`

# 用户模板信息 mongo(废)

- userid **建立唯一索引**

- temp:{

  ​	temp1 label:{

  ​        name:

  ​		parents:[label1,label2],

  ​		children:[label1, label2]

  ​		avataruri: 图像

  ​        isNode: 关系还是节点

  ​        isAbstract: 是否为抽象模板（不为子模板添加label）

  ​		properties:{

  ​			prop1:default value,

  ​		}

  ​	}

   }

`db.testUser.update({userName:"Allen"},{$set:{"properties.nest2":{name:"someth",avatar:"dsfsd/fdsff/dfdf",props:{width:12}}}})`



$unset 删除字段

apis

- 添加模板
- 删除模板
- 更新模板(图像，抽象否，属性
- 查询模板们







# APIs

## 用户

- 注册：

  - `post`  /auth/register

  - ```javascript
    {
        username
        password
        email
        phoneNumber
    }
    ```

    

- 登录：

  - `post` /auth/login

  - ```js
    username
    password
    ```

- 更新token

  - `get` /auth/refresh  需Authorization字段



## 用户文件

- 获取所有文件

  - `get` /file

- 添加一个文件

  - `post` /file

  - ```js
    {
        parentId 
        fileName *必须
        folder 
    }
    ```

- 更新（patch)一个文件

  - `patach` /file/{fileId}

  - ```js
    {
        ** x-www-form-urlencoded
        
        parentId
        fileName
        folder
    }
    ```

- 更新（put)一个文件

  - `put` /file/{fileId}

  - ```
    {
    	parentId 
    	fileName
    	folder
    }
    ```

- 删除一个文件

  - `delete` /file/{fileId}



## 用户模板

- 获取所有模板

  - `get` /module

- 添加一个模板

  - `post` /module

  - ```
    { 
    	labelName:String
    	describe:String
    	node:boolean
    	abstr:boolean
    	parentIds:[]
    	properties:Object
    }
    ```

- 更新一个模板

  - `patch` /module/{moduleId}

  - **raw json**  NOT x-www-form-urlencoded

  - ```
    {
    	rawMap:{
    		labelName
    		..
    	}
    }
    ```

  - 

- 更新一个模板

  - `put` /module/{moduleId}
  - 同上

- 删除一个模板

  - `delete`  /module/{moduleId}





## 图片

- 用户头像
  - `post` /image/useravatar
  - form-data file **image**
- 模板icon
  - `post` /image/moduleavatar/{moduleId}
  - 同上
  - 、
- 获取图片
  - `get` /image/{image name}
  - **不安全，未进行参数检查**



## 关系图

patch: raw json

文件所有节点的查询，删除

单个节点的增删查改

/graph/node[/{fileId}[/{nodeId}]]

```
{
	labels:[]
	properties:{}
	mainLabel:String
	delete:boolean
}
```



文件所有关系的查询，删除

单个关系的增删查改

/graph/relation[/{fileId}[/{relationId}]]

```
{
	relationName:String
	properties:{}
	delete:boolean
	
	*if warp
	startId:Long
	endId:Long
	
	relaUnit
}
```



