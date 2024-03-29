# auction-sniper
『実践テスト駆動開発』で、「動くサンプル」として AuctionSniper という架空のシステムが解説に用いられているが、全てのソースが掲載されているわけではないため、足りない部分を補完した。

また一旦ひと通り実装した後で、「オブジェクト指向エクササイズ」の９つのルールをできるだけ適用して、テストを壊さないようにリファクタした。

#### 実践テスト駆動開発
Agile開発／TDDの専門家で jMock の開発者でもある Steve Freeman, Nat PryceによるTDD 解説の良書。原題 "Growing Object-Oriented Software"。  

受け入れテスト／インテグレーションテスト／ユニットテストの位置づけ、効果的なモックの使い方、その他TDDの実践的な教訓について詳しく解説されている。

#### オブジェクト指向エクササイズ
『ThoughtWorks アンソロジー』で提案されている OOPの技術を向上させるための方策。
以下のやや厳しい ９つのルールを1000行程度のプログラムに適用してみることで、ベターなコードを書く発想を身につけるというもの。（ThoughtWorks社では実際のプロダクトコードでも一部用いているらしい。）
1. １つのメソッドにつきインデントは１段階まで
2. else 句を使用しない
3. すべてのプリミティブ型と文字列型をラップする
4. １行につきドットは１つまでにする
5. 名前を省略しない
6. すべてのエンティティを小さくする
7. １つのクラスにつきインスタンス変数は２つまでにする
8. ファーストクラスコレクションを使用する
9. Getter, Setter, プロパティを使用しない
