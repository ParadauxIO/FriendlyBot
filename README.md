# FriendlyBot
![Build](https://github.com/ParadauxIO/FriendlyBot/workflows/Build/badge.svg?branch=main)
![Version](https://img.shields.io/badge/version-1.0.0-009999)
![License](https://img.shields.io/github/license/ParadauxIO/FriendlyBot)

<br />
<p align="center">
  <a href="https://github.com/ParadauxIO/FriendlyBot">
    <img src="images/FriendlyBot.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">FriendlyBot</h3>
  <p align="center">
    The general-purpose discord bot made for the <a href="https://csfriendlycorner.com">Computer Science Friendly Corner</a>.
    <br />
    <a href="https://paradaux.myjetbrains.com/youtrack/projects/821cb79e-73d0-4a28-b06d-ce495ffb1b13">YouTrack</a>
    ·
    <a href="https://github.com/ParadauxIO/FriendlyBot/blob/main/CONTRIBUTING.md">Contributing</a>
    ·
    <a href="https://github.com/ParadauxIO/FriendlyBot/issues">Issues</a>
    ·
    <a href="https://github.com/ParadauxIO/FriendlyBot/actions">Actions</a>
  </p>
</p>

There are plans to generify the codebase to allow for a public discord bot, as well as having a web-based user interface
for controlling the public instance of the discord bot, and to provide a facility for users to download a copy of the data
the bot stores on them.

This bot utilises semantic versioning on a rolling-release cycle. Pushes to main represent what is currently running on the
production server. 

## Installation

FriendlyBot makes use of a MongoDB database which is integral to the functionality of the bot, the connection url must 
be specified in the configuration file. As well as that, a stable internet connection is required at all times; to talk to
discord and the various web APIs the bot makes use of. 

The following instructions are to be executed on the command line (terminal) requiring write permissions. 
This requires both Maven and Git to be added to your terminal's path, other than that these instructions are fairly platform agnostic.

```bash
# Download the source
git clone https://github.com/ParadauxIO/FriendlyBot.git 

# Enter the source directory
cd FriendlyBot 

# Compile the source, and pull dependencies.
mvn -B package --file pom.xml
```

## Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/MyNewChange`)
3. Commit your Changes (`git commit -m 'Add some MyNewChange'`)
4. Push to the Branch (`git push origin feature/MyNewChange`)
5. Open a Pull Request
6. Profit ???

## License

Sources are Copyright © Rían Errity 2020-2021 unless stated otherwise. 

Distributed under the MIT License. See [LICENSE.md](https://github.com/ParadauxIO/FriendlyBot/blob/main/LICENSE) for more information.

## Contributors

1. [Rían Errity](mailto:rian@paradaux.io) — Project Lead — https://paradaux.io