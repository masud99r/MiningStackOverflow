import sqlite3
def createdb(dbname):
    conn = sqlite3.connect(dbname)
    c = conn.cursor()

    # Create table
    c.execute('''CREATE TABLE stackposts
                 (Id TEXT,
                 PostTypeId TEXT,
                 ParentID TEXT,
                 AcceptedAnswerId TEXT,
                 CreationDate TEXT,
                 Score REAL,
                 ViewCount REAL,
                 Body TEXT,
                 OwnerUserId TEXT,
                 LastEditorUserId TEXT,
                 LastEditDate TEXT,
                 LastActivityDate TEXT,
                 CommunityOwnedDate TEXT,
                 ClosedDate TEXT,
                 Title TEXT,
                 Tags TEXT,
                 AnswerCount REAL,
                 CommentCount REAL,
                 FavoriteCount REAL)''')
    conn.close()

def insertodb():
    conn = sqlite3.connect('sqlite-db/stackpost_sample.db')
    c = conn.cursor()
    # Insert a row of data
    c.execute("INSERT INTO stocks VALUES ('2006-01-05','BUY','RHAT',100,35.14)")

    # Save (commit) the changes
    conn.commit()

    # We can also close the connection if we are done with it.
    # Just be sure any changes have been committed or they will be lost.
    conn.close()
def querysql():
    conn = sqlite3.connect('sqlite-db/stackpost_sample.db')
    c = conn.cursor()
    symbol = 'RHAT'
    c.execute("SELECT * FROM stocks WHERE symbol = '%s'" % symbol)

    # Do this instead
    t = ('RHAT',)
    c.execute('SELECT * FROM stocks WHERE symbol=?', t)
    print(c.fetchone())

    # Larger example that inserts many records at a time
    purchases = [('2006-03-28', 'BUY', 'IBM', 1000, 45.00),
                 ('2006-04-05', 'BUY', 'MSFT', 1000, 72.00),
                 ('2006-04-06', 'SELL', 'IBM', 500, 53.00),
                 ]
    c.executemany('INSERT INTO stocks VALUES (?,?,?,?,?)', purchases)
    for row in c.execute('SELECT * FROM stocks ORDER BY price'):
        print(row)
def dumpxml(dbname, xmlfile):
    conn = sqlite3.connect(dbname)
    c = conn.cursor()

    import xml.etree.ElementTree
    e = xml.etree.ElementTree.parse(xmlfile).getroot()
    for atype in e.findall('row'):
        Id = atype.get('Id'),
        PostTypeId = atype.get('PostTypeId'),
        ParentID = atype.get('ParentID'),
        AcceptedAnswerId = atype.get('AcceptedAnswerId'),
        CreationDate = atype.get('CreationDate'),
        Score = float(atype.get('Score')),
        print (atype.get('ViewCount'))

        ViewCount = float(atype.get('ViewCount')),
        Body = atype.get('Body'),
        OwnerUserId = atype.get('OwnerUserId'),
        LastEditorUserId = atype.get('LastEditorUserId'),
        LastEditDate = atype.get('LastEditDate'),
        LastActivityDate = atype.get('LastActivityDate'),
        CommunityOwnedDate = atype.get('CommunityOwnedDate'),
        ClosedDate = atype.get('ClosedDate'),
        Title = atype.get('Title'),
        Tags = atype.get('Tags'),
        AnswerCount = float(atype.get('AnswerCount')),
        CommentCount = float(atype.get('CommentCount')),
        FavoriteCount = float(atype.get('FavoriteCount'))
        #try:
            #inser to database
        post = [(Id, PostTypeId, ParentID, AcceptedAnswerId, CreationDate, Score, ViewCount, Body, OwnerUserId, LastEditorUserId, LastEditDate, LastActivityDate, CommunityOwnedDate, ClosedDate, Title, Tags, AnswerCount, CommentCount, FavoriteCount),
                (Id, PostTypeId, ParentID, AcceptedAnswerId, CreationDate, Score, ViewCount, Body, OwnerUserId,
                 LastEditorUserId, LastEditDate, LastActivityDate, CommunityOwnedDate, ClosedDate, Title, Tags,
                 AnswerCount, CommentCount, FavoriteCount),]
        c.execute('INSERT INTO stackposts VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)', Id, PostTypeId, ParentID, AcceptedAnswerId, CreationDate, Score, ViewCount, Body, OwnerUserId, LastEditorUserId, LastEditDate, LastActivityDate, CommunityOwnedDate, ClosedDate, Title, Tags, AnswerCount, CommentCount, FavoriteCount)
        #c.execute('INSERT INTO stackposts VALUES (Id+","+ PostTypeId+","+ParentID+","+AcceptedAnswerId+","+CreationDate+","+Score+","+ViewCount+","+Body+","+OwnerUserId+","+LastEditorUserId+","+LastEditDate+","+LastActivityDate+","+CommunityOwnedDate+","+ClosedDate+","+Title+","+Tags+","+AnswerCount+","+CommentCount+","+ FavoriteCount)')
        conn.commit()
        #except:
        #    print ("Problem inserting to db "+ str(Id))
    conn.close()

if __name__ == '__main__':
    #xml2sqlite()
    #querysql()
    dbname = 'sqlite-db/stackpost_sample.db'
    xmlfile = "K:/Masud/Datasets/stackoverflow.com-Posts/sample-Posts.xml"
    #createdb(dbname)
    dumpxml(dbname, xmlfile)