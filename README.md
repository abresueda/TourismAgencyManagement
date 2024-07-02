# TourismAgencyManagement
Turizm Acentesi Otel Yönetim Sistemi
Proje Tanımı
Turizm Acentesi'nin otel sektöründe faaliyet gösteren işletmesinin günlük operasyonlarını daha etkili bir şekilde yönetmesini sağlamak ve müşteri rezervasyon süreçlerini optimize etmek amacıyla dijital bir altyapı geliştirilmiştir. Bu sistem, acentenin kağıt üzerinde ve manuel olarak yaptığı işlemleri dijital ortamda kolay ve hızlı bir şekilde gerçekleştirmesini sağlayacaktır.

Kullanıcı Rolleri ve Yetkiler
Sistemde iki tür kullanıcı rolü bulunmaktadır: Admin ve Acente Çalışanı (Personel). Kullanıcıların yetkileri aşağıdaki gibidir:

Admin
Kullanıcı Yönetimi:
Acente çalışanlarını listeleme
Ekleme
Silme
Güncelleme
Kullanıcı rolüne göre filtreleme

Acente Çalışanı (Personel)
Otel Yönetimi: Otel listeleme, ekleme
Oda Yönetimi: Oda listeleme, ekleme
Dönem Yönetimi: Dönem listeleme, ekleme
Fiyat Yönetimi
Oda Arama
Rezervasyon İşlemleri: Rezervasyon listeleme, ekleme, silme, güncelleme
Fonksiyonel Gereksinimler

Kullanıcı Yönetimi
Admin, kullanıcıları ekleyip çıkarabilir ve bilgilerini düzenleyebilir.
Kullanıcılar, username ve password girerek sisteme giriş yapar.
Admin, kullanıcı rolüne göre filtreleme yapabilir.

Otel Yönetimi
Otel ekleme: Otel Adı, Adres, E-posta, Telefon, Yıldız, Tesis Özellikleri, Pansiyon tipleri
Otelleri listeleme ve düzenleme
Otellerin pansiyon tipi, tesis özelliği ve dönem bilgilerini kaydetme

Dönem Yönetimi
Otellere ait tarihsel dönemler eklenir ve oda fiyatlandırmalarında bu dönemler dikkate alınır.
Dönemler iki tarih aralığı olarak tanımlanır.

Oda Yönetimi
Oda ekleme: Otel, oda tipi, pansiyon tipi, dönem, yetişkin ve çocuk için gecelik fiyat, stok adedi
Oda özellikleri: Yatak sayısı, metrekare, televizyon, minibar, oyun konsolu, kasa, projeksiyon

Oda Arama ve Rezervasyon İşlemleri
Tarih aralığına, şehire ve otel adına göre oda arama
Dinamik SQL sorguları ile esnek arama
Rezervasyon işlemi: Müşteri iletişim bilgileri, misafir bilgileri (Ad, Soyad, T.C. Kimlik Bilgileri)
Rezervasyon listesi, güncelleme ve silme


Teknik Gereksinimler
Veritabanı: MySQL veya PostgreSQL
GUI (Arayüz) Tasarımı: Swing, JavaFX vb. kullanılarak kullanıcı dostu bir arayüz tasarlanmıştır.

Veritabanı Tabloları:
user: Admin ve acente çalışanı kullanıcı bilgileri
otel: Sisteme kayıtlı otel bilgileri
season: Otellere ait sezon kayıtları (otel_id)
pension_type: Otellere ait pansiyon tipi kayıtları (otel_id)
room: Otellere ait oda kayıtları (otel_id, season_id, pension_type_id)
reservation: Odaya ait rezervasyon kayıtları (room_id)

Proje Kurulumu ve Kullanımı
Veritabanı Kurulumu:

MySQL veya PostgreSQL veritabanınızı kurun.
Yukarıda belirtilen tabloları oluşturun.
Proje Dosyalarının İndirilmesi:

Proje dosyalarını bu bağlantıdan indirin.
Yapılandırma:

Veritabanı bağlantı bilgilerini yapılandırma dosyasında (ör. config.properties) ayarlayın.
Çalıştırma:

Projeyi IDE'nizde açın ve çalıştırın.
Giriş ekranından admin veya acente çalışanı olarak sisteme giriş yapın.
Kullanıcı Yönetimi:

Admin olarak giriş yaparak yeni kullanıcılar ekleyin ve mevcut kullanıcıları yönetin.
Otel, Oda ve Rezervasyon İşlemleri:

Acente çalışanı olarak giriş yaparak otel ve oda ekleme işlemlerini gerçekleştirin.
Rezervasyon işlemlerini yapın ve mevcut rezervasyonları yönetin.
