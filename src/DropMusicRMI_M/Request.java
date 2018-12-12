package DropMusicRMI_M;

/**
 * Class that contains request codes used in MulticastServer and RMIServer
 */
public class Request {
    //Features
    protected static final int LOGIN             = 1;  //fazer login
    protected static final int LOGOUT            = 14; //Logout
    protected static final int REGISTER          = 29; //fazer registro
    protected static final int MANAGE            = 2;  //gerir artistas, musicas e albuns
    protected static final int SEARCH            = 3;  //procurar musicas por album ou artista
    protected static final int DETAILS_ALBUM     = 60; //Consultar detalhes de album
    protected static final int DETAILS_ARTIST    = 61; //Consultar detalhes de artista
    protected static final int CRITIQUE          = 5;  //escrever critica a album
    protected static final int MAKE_EDITOR       = 6;  //dar privilegios de editor a user
    protected static final int NOTE_EDITOR       = 7;  //notificação de novo editor
    protected static final int NOTE_NEW_EDIT     = 8;  //notificaçao de novo edit
    protected static final int NOTE_DELIVER      = 9;  //entregar notificação a user previ. off
    protected static final int UPLOAD            = 10; //fazer upload de uma musica para o server
    protected static final int SHARE             = 11; //Share de uma musica com users
    protected static final int DOWNLOAD          = 12; //Download de musicas do servidor
    protected static final int CALLBACK          = 13; //Packet returned after processing in server
    protected static final int UP_TCP            = 62; //Devolve o endereço do servidor para dar upload
    protected static final int DOWN_TCP          = 70; //Devolve o endereço do servidor para fazer download
    protected static final int NEW_SERVER        = 30; //Novo servidor criado, novos pacotes podem escolhe-lo
    protected static final int SERVER_DOWN       = 31; //Servidor foi abaixo, novos pacotes não o escolhem
    protected static final int UPLOAD_MUSIC      = 38; //Upload de musicas para o servidor

    //Edit
    protected static final int EDIT_ALBUM        = 15; //Editar info de albums
    protected static final int EDIT_MUSIC        = 16; //Editar info de musicas
    protected static final int EDIT_ARTIST       = 17; //Editar info de artistas
    protected static final int EDIT_NAME         = 18; //Nome do item
    protected static final int EDIT_YEAR         = 19; //Para items que tenham ano
    protected static final int EDIT_FIELD_ARTIST = 20; //Para items que tenham o campo "artist"
    protected static final int EDIT_DESCRIPTION  = 21; //Descrição do item
    protected static final int EDIT_GENRE        = 22; //Para items que tenham o campo "genre"
    protected static final int EDIT_LYRICS       = 23; //Letras de musicas
    protected static final int EDIT_BIRTH        = 24; //Data de nascimento de um artista
    protected static final int EDIT_FIELD_ALBUMS = 25; //Lista de albums de um artista
    protected static final int ADD_ARTIST        = 32; //Adiciona artistas à base de dados
    protected static final int ADD_ALBUM         = 33; //Adiciona albums à base de dados
    protected static final int ADD_MUSIC         = 34; //Adiciona musicas à base de dados
    protected static final int REMOVE_ARTIST     = 35; //Remove um artista
    protected static final int REMOVE_ALBUM      = 36; //Remove um album
    protected static final int REMOVE_MUSIC      = 37; //Remove uma musica da base de dados

    //Pesquisa
    protected static final int SEARCH_ALBUM      = 26; //Pesquisa relacionada com albums
    protected static final int SEARCH_MUSIC      = 27; //Pesquisa relacionada com musicas
    protected static final int SEARCH_ARTIST     = 28; //Pesquisa relacionada com artistas
    protected static final int SEARCH_BY_NAME    = 39; //Pesquisa por nome
    protected static final int SEARCH_BY_ARTIST  = 40; //Pesquisa por artista
    protected static final int SEARCH_BY_GENRE   = 41; //Pesquisa por genero
    protected static final int SEARCH_BY_ALBUM   = 42; //Pesquisa por album

    //Internal use only
    protected static final int REQUEST_NUMBER    = 43; //Internal use only
    protected static final int ASSIGN_NUMBER     = 44; //Internal use only
    protected static final int CHECK_SERVER_UP   = 45; //Internal use only
    protected static final int DB_ACCESS         = 46; //Internal use only
    protected static final int DB_ANSWER         = 47; //Internal use only
    protected static final int CHECK_LOGIN_STATE = 48; //Internal use only
    protected static final int NAME_EXISTS       = 49; //Internal use only
    protected static final int UPDATE_LOGIN_STATE= 50; //Internal use only
    protected static final int GET_NOTES         = 51; //Internal use only
    protected static final int CLEAR_NOTES       = 52; //Internal use only
    protected static final int CHECK_USER_EXISTS = 53; //Internal use only
    protected static final int REGISTER_USER     = 54; //Internal use only
    protected static final int TIMEOUT           = 55; //Internal use only
    protected static final int CHECK_EDITOR_STATE= 56; //Internal use only
    protected static final int ATTRIBUTE_EDIT    = 57; //Internal use only
    protected static final int ADD_ITEM          = 58; //Internal use only
    protected static final int REMOVE_ITEM       = 59; //Internal use only
    protected static final int STORAGE_ACCESS    = 63;
    protected static final int STORAGE_IP        = 64;
    protected static final int STORAGE_ANSWER    = 65;
    protected static final int ALL_EDITORS       = 66;
    protected static final int CHECK_SHARE_USERS = 67; //internal, code to check in db if are users sharing already
    protected static final int ADD_SHARE_USERS   = 68; //internal
    protected static final int CHECK_UPLOADER    = 69;
}
