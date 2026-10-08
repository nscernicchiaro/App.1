# Como gerar o APK do Medição de Campo

Este projeto já vem com o endereço da sua planilha configurado. Para virar um `.apk`, ele precisa ser compilado uma vez. Escolha **um** dos dois caminhos abaixo.

---

## Caminho A: GitHub (sem instalar nada no computador)

1. Crie uma conta grátis em github.com, se ainda não tiver.
2. Clique em **New repository**. Dê o nome `medicao-campo`, marque **Private** e clique em **Create repository**.
3. Clique em **uploading an existing file** e arraste **todo o conteúdo** desta pasta (`app`, `gradle`, `gradlew`, `build.gradle` etc.). Depois clique em **Commit changes**.
4. A pasta `.github` pode não ser enviada, porque fica oculta no computador. Se isso acontecer, crie o arquivo à mão:
   - Clique em **Add file > Create new file**.
   - No nome, digite `.github/workflows/gerar-apk.yml`.
   - Cole o conteúdo do arquivo `gerar-apk.yml` (está na pasta `.github/workflows` deste projeto) e clique em **Commit changes**.
5. Abra a aba **Actions**. A compilação "Gerar APK" leva de 3 a 5 minutos. Quando aparecer o ✓ verde, clique nela.
6. Em **Artifacts**, baixe **medicao-campo-apk**. É um .zip com o `app-debug.apk` dentro.

## Caminho B: Android Studio (no seu computador)

1. Instale o Android Studio (developer.android.com/studio). Ele é grátis e ocupa cerca de 3 GB.
2. Clique em **Open** e escolha esta pasta. Espere terminar a sincronização (barra embaixo); na primeira vez, demora alguns minutos.
3. No menu, vá em **Build > Build App Bundle(s) / APK(s) > Build APK(s)**.
4. Quando terminar, clique em **locate**. O arquivo é `app/build/outputs/apk/debug/app-debug.apk`.

---

## Instalar no celular Android

1. Envie o `app-debug.apk` para o celular (WhatsApp, e-mail, Google Drive ou cabo).
2. Toque no arquivo. O Android vai pedir para **permitir a instalação deste app de fontes desconhecidas**. Ative e volte.
3. Se aparecer o aviso do **Play Protect**, toque em **Mais detalhes > Instalar mesmo assim**. O aviso aparece porque o app não veio da Play Store.
4. O app **Medição** aparece na tela inicial.

No primeiro acesso, o encarregado escolhe o nome e digita o PIN cadastrado na aba **Encarregados** da planilha. Esse primeiro acesso precisa de internet.

## Atualizar o app depois

- Para mudar itens ou encarregados, basta editar a planilha. Ninguém precisa reinstalar nada. No app, toque em **Atualizar itens**.
- Para mudar telas ou funções, altere `app/src/main/assets/www/index.html` e aumente `versionCode` em `app/build.gradle` (1 → 2). Depois gere e instale o APK novo por cima do antigo. As medições guardadas no celular continuam lá.
- Se você reimplantar o script com uma **nova implantação**, a URL muda. Use **Gerenciar implantações > Editar > Nova versão** para manter a mesma URL.

## Observação

Este é um APK de **teste** ("debug"). Ele serve para instalar diretamente nos celulares da equipe. Para publicar na Play Store, é preciso gerar uma versão assinada ("release"). Isso fica para quando o teste estiver aprovado.
